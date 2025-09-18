package com.qlda.sontay.service.auth.impl;

import com.qlda.sontay.config.MyUserDetail;
import com.qlda.sontay.domain.Token;
import com.qlda.sontay.domain.User;
import com.qlda.sontay.exception.payload.DataNotFoundException;
import com.qlda.sontay.repository.auth.TokenRepository;
import com.qlda.sontay.repository.auth.UserRepository;
import com.qlda.sontay.service.auth.AuthenticationService;
import com.qlda.sontay.service.auth.JwtService;
import com.qlda.sontay.dto.auth.request.AuthenticationRequest;
import com.qlda.sontay.dto.auth.response.AuthenticationResponse;
import com.qlda.sontay.dto.auth.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.qlda.sontay.common.UserStatus.ACTIVE;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${security.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpirationSeconds;

    @Value("${security.jwt.token-validity-in-seconds}")
    private long accessTokenExpirationSeconds;

    @Value("${security.jwt.refresh-token-validity-in-seconds-for-remember-me}")
    private long refreshTokenExpirationSecondsRememberMe;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // 1) Lấy user + roles
        User user = userRepository
                .getByUsernameAndIsDeleted(request.getUsername(), false)
                .orElseThrow(() -> new RuntimeException("User not found or deleted"));

        // 2) Trạng thái
        if (!ACTIVE.equals(user.getStatus())) {
            throw new RuntimeException("User is not activated");
        }

        // 3) Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 4) Tạo token
        boolean rememberMe = Boolean.TRUE.equals(request.getIsRememberMe());
        long rtTtl = rememberMe ? refreshTokenExpirationSecondsRememberMe : refreshTokenExpirationSeconds;

        MyUserDetail myUserDetail = new MyUserDetail(user);
        String accessToken = jwtService.generateAccessToken(myUserDetail, rememberMe);
        String refreshToken = jwtService.generateRefreshToken(myUserDetail, rememberMe);

        log.info("Authorities: {}" , myUserDetail.getAuthorities());

        Instant now = Instant.now();
        Instant accessExp = now.plusSeconds(accessTokenExpirationSeconds);
        Instant refreshExp = now.plusSeconds(rtTtl);

        // Nếu user đã có token → update thay vì insert
        Token dbToken = tokenRepository.findByUser(user).orElse(new Token());
        dbToken.setUser(user);
        dbToken.setToken(accessToken);
        dbToken.setRefreshToken(refreshToken);
        dbToken.setTokenType("Bearer");
        dbToken.setExpirationTime(accessExp);
        dbToken.setRefreshExpirationTime(refreshExp);
        dbToken.setRevoked(false);
        dbToken.setExpired(false);
        tokenRepository.save(dbToken);

        return createAuthenticationResponse(accessToken, refreshToken, myUserDetail);
    }


    @Transactional
    @Override
    public AuthenticationResponse loginWithToken(String token) {
        String userName = jwtService.extractUsername(token);
        User user = this.userRepository.getByUsernameAndIsDeleted(userName, false).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return createAuthenticationResponse(token, user.getToken().getRefreshToken(), new MyUserDetail(user));
    }

    @Transactional
    public AuthenticationResponse refreshToken(String rawRefreshToken){

        Token token = verifyRefreshToken(rawRefreshToken);

        if (token.getRefreshExpirationTime() == null || !token.getRefreshExpirationTime().isAfter(Instant.now())) {
            throw new RuntimeException("RefreshToken is expired!");
        }

        User user = token.getUser();
        MyUserDetail myUserDetail = new MyUserDetail(user);

        // Tạo mới cặp token
        String newAccessToken  = jwtService.generateAccessToken(myUserDetail, false);
        String newRefreshToken = jwtService.generateRefreshToken(myUserDetail, false);

        // Cập nhật đầy đủ record
        token.setToken(newAccessToken);
        token.setExpirationTime(Instant.now().plusSeconds(accessTokenExpirationSeconds));
        token.setRefreshToken(newRefreshToken);
        token.setRefreshExpirationTime(Instant.now().plusSeconds(refreshTokenExpirationSeconds));

        tokenRepository.save(token);

        return createAuthenticationResponse(newAccessToken, newRefreshToken, myUserDetail);
    }

    public void logout() {
        // 1. Lấy thông tin người dùng đã xác thực từ Security Context
        // `principal` ở đây chính là đối tượng `MyUserDetail` mà bạn đã tạo khi xác thực
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof MyUserDetail) {
            MyUserDetail userDetails = (MyUserDetail) principal;
            User currentUser = userDetails.user();

            if (currentUser != null) {
                // 2. Tìm token của người dùng trong DB
                Token dbToken = tokenRepository.findByUser(currentUser)
                    .orElse(null); // Có thể không tìm thấy nếu đã bị xóa/logout từ thiết bị khác

                if (dbToken != null) {
                    // 3. Vô hiệu hóa token
                    dbToken.setRevoked(true);
                    dbToken.setExpired(true);

                    // Bạn cũng có thể xóa hẳn token và refresh token để dọn dẹp DB
                    // dbToken.setToken(null);
                    // dbToken.setRefreshToken(null);

                    // 4. Lưu lại vào DB
                    tokenRepository.save(dbToken);

                    // 5. Xóa SecurityContext để hoàn tất quá trình logout
                    SecurityContextHolder.clearContext();

                    log.info("User {} has been logged out successfully.", currentUser.getUsername());
                } else {
                    log.warn("No token found for user {} during logout.", currentUser.getUsername());
                }
            }
        } else {
            log.error("The principal is not an instance of MyUserDetail. Cannot process logout.");
        }
    }


    private AuthenticationResponse createAuthenticationResponse(String token, String refreshToken, MyUserDetail myUserDetail) {
        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .token(token)
                .refreshToken(refreshToken)
                .user(UserResponse.builder()
                        .id(myUserDetail.user().getId())
                        .username(myUserDetail.user().getUsername())
                        .email(myUserDetail.user().getEmail())
                        .phone(myUserDetail.user().getPhone())
                        .birthday(myUserDetail.user().getBirthday())
                        .gender(myUserDetail.user().getGender())
                        .fullName(myUserDetail.user().getFullName())
                        .build())
                .build();
    }

    public Token verifyRefreshToken(String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new DataNotFoundException("Invalid refresh token"));

        if (token.getExpirationTime().compareTo(Instant.now()) < 0) {
            tokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }

        return token;
    }


}

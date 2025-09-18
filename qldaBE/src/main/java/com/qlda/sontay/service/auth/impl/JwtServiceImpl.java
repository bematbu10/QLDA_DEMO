package com.qlda.sontay.service.auth.impl;

import com.qlda.sontay.config.MyUserDetail;
import com.qlda.sontay.domain.User;
import com.qlda.sontay.service.auth.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j(topic = "JWT-SERVICE")
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {


    @Value("${jhipster.security.authentication.jwt.base64-secret}")
    private String secretKey;
    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds}")
    private long jwtExpiration;
    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds-for-remember-me}")
    private long jwtExpirationRememberMe;
    @Value("${security.jwt.refresh-token-validity-in-seconds}")
    private long refreshExpiration;
    @Value("${security.jwt.refresh-token-validity-in-seconds-for-remember-me}")
    private long refreshExpirationRememberMe;

    public static final String CLAIM_USER_ID = "id";

    public static final String CLAIM_USER_FULL_NAME = "fullName";

    private final UserDetailsService userDetailsService;

    @Override
    public boolean isValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public boolean isTokenValid(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return !claims.getExpiration().before(new Date());
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public String generateRefreshToken(MyUserDetail myUserDetail, boolean isRememberMe) {
        return buildToken(new HashMap<>(), myUserDetail, isRememberMe ? refreshExpirationRememberMe : refreshExpiration);
    }

    public String generateAccessToken(MyUserDetail myUserDetail, boolean isRememberMe) {
        User user = myUserDetail.user();
        Map<String, Object> extractClaims = Map.ofEntries(
            Map.entry(CLAIM_USER_ID, user.getId()),
            Map.entry(CLAIM_USER_FULL_NAME, user.getUsername())
        );
        return generateToken(extractClaims, myUserDetail, isRememberMe);
    }

    public String generateToken(
        Map<String, Object> extraClaims,
        MyUserDetail myUserDetail,
        boolean isRememberMe
    ) {
        return buildToken(extraClaims, myUserDetail, isRememberMe ? jwtExpirationRememberMe : jwtExpiration);
    }

    public String buildToken(Map<String, Object> extractClaims, MyUserDetail myUserDetail, long expiration) {
        try {
            return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(myUserDetail.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (expiration + 1000)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

}

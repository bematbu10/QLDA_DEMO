package com.qlda.sontay.service.auth;

import com.qlda.sontay.config.MyUserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    UsernamePasswordAuthenticationToken getAuthentication(String token);
    boolean isTokenValid(String token);
    String generateAccessToken(MyUserDetail myUserDetail, boolean isRememberMe);
    String generateRefreshToken(MyUserDetail myUserDetail, boolean isRememberMe);
    String extractUsername(String token);
    boolean isValid(String token, UserDetails user);
}

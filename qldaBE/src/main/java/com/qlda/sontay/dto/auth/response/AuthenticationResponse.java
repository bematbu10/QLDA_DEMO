package com.qlda.sontay.dto.auth.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AuthenticationResponse {
    Boolean isAuthenticated;
    String token;
    String refreshToken;
    UserResponse user;
}

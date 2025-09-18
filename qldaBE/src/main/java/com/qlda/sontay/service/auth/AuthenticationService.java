package com.qlda.sontay.service.auth;

import com.qlda.sontay.dto.auth.request.AuthenticationRequest;
import com.qlda.sontay.dto.auth.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse loginWithToken(String token);

    AuthenticationResponse refreshToken(String refreshToken);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout();

}

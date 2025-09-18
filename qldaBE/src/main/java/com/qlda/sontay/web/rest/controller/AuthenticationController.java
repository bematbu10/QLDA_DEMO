package com.qlda.sontay.web.rest.controller;

import com.qlda.sontay.service.auth.AuthenticationService;
import com.qlda.sontay.dto.common.IResponseMessage;
import com.qlda.sontay.dto.common.SuccessResponseMessage;
import com.qlda.sontay.dto.auth.request.AuthenticationRequest;
import com.qlda.sontay.dto.auth.request.RefreshTokenRequest;
import com.qlda.sontay.dto.auth.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate")
@Slf4j(topic = "AUTHENTICATION-CONTROLLER")
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("")
    ResponseEntity<IResponseMessage> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticateResponse = this.authenticationService.authenticate(request);
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticateResponse));
    }


    @PostMapping("/login-with-token")
    ResponseEntity<IResponseMessage> loginWithToken(@RequestBody RefreshTokenRequest req) {
        AuthenticationResponse authenticationResponse = this.authenticationService.loginWithToken(req.getRefreshToken());
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticationResponse));
    }

    @PostMapping("/refresh-token")
    ResponseEntity<IResponseMessage> login(@RequestBody RefreshTokenRequest req) {
        AuthenticationResponse authenticateResponse = this.authenticationService.refreshToken(req.getRefreshToken());
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticateResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<IResponseMessage> logout() {
        authenticationService.logout();
        return ResponseEntity.ok(SuccessResponseMessage.LoadedSuccess("Logged out successfully."));
    }
}

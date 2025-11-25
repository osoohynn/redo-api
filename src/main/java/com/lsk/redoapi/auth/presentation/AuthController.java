package com.lsk.redoapi.auth.presentation;

import com.lsk.redoapi.auth.presentation.dto.request.LoginRequest;
import com.lsk.redoapi.auth.presentation.dto.request.SignupRequest;
import com.lsk.redoapi.auth.presentation.dto.response.AuthResponse;
import com.lsk.redoapi.auth.service.AuthService;
import com.lsk.redoapi.global.common.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<AuthResponse>> signup(@Valid @RequestBody SignupRequest request) {
        AuthResponse response = authService.signup(request);
        return BaseResponse.created(response);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return BaseResponse.ok(response);
    }
}
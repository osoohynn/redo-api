package com.lsk.redoapi.auth.service;

import com.lsk.redoapi.auth.presentation.dto.request.LoginRequest;
import com.lsk.redoapi.auth.presentation.dto.request.SignupRequest;
import com.lsk.redoapi.auth.presentation.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}

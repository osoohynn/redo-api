package com.lsk.redoapi.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsk.redoapi.auth.presentation.dto.request.LoginRequest;
import com.lsk.redoapi.auth.presentation.dto.request.SignupRequest;
import com.lsk.redoapi.auth.presentation.dto.response.AuthResponse;
import com.lsk.redoapi.auth.service.AuthService;
import com.lsk.redoapi.global.exception.CustomException;
import com.lsk.redoapi.global.exception.ErrorCode;
import com.lsk.redoapi.global.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController 테스트")
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("POST /api/auth/signup - 회원가입 성공")
    void signup_success() throws Exception {
        // given
        SignupRequest request = new SignupRequest();
        ReflectionTestUtils.setField(request, "username", "testuser");
        ReflectionTestUtils.setField(request, "password", "password123");

        AuthResponse response = AuthResponse.builder()
                .token("jwt-token-here")
                .username("testuser")
                .build();

        given(authService.signup(any(SignupRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    @DisplayName("POST /api/auth/signup - 실패 (중복된 사용자명)")
    void signup_duplicateUsername() throws Exception {
        // given
        SignupRequest request = new SignupRequest();
        ReflectionTestUtils.setField(request, "username", "existinguser");
        ReflectionTestUtils.setField(request, "password", "password123");

        given(authService.signup(any(SignupRequest.class)))
                .willThrow(new CustomException(ErrorCode.DUPLICATE_USERNAME));

        // when & then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /api/auth/signup - 실패 (@Valid 검증 실패 - 빈 username)")
    void signup_validationFail() throws Exception {
        // given - username이 빈 문자열
        SignupRequest request = new SignupRequest();
        ReflectionTestUtils.setField(request, "username", "");
        ReflectionTestUtils.setField(request, "password", "password123");

        // when & then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/login - 로그인 성공")
    void login_success() throws Exception {
        // given
        LoginRequest request = new LoginRequest();
        ReflectionTestUtils.setField(request, "username", "testuser");
        ReflectionTestUtils.setField(request, "password", "password123");

        AuthResponse response = AuthResponse.builder()
                .token("jwt-token-here")
                .username("testuser")
                .build();

        given(authService.login(any(LoginRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    @DisplayName("POST /api/auth/login - 실패 (잘못된 비밀번호)")
    void login_invalidPassword() throws Exception {
        // given
        LoginRequest request = new LoginRequest();
        ReflectionTestUtils.setField(request, "username", "testuser");
        ReflectionTestUtils.setField(request, "password", "wrongpassword");

        given(authService.login(any(LoginRequest.class)))
                .willThrow(new CustomException(ErrorCode.INVALID_PASSWORD));

        // when & then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/auth/login - 실패 (@Valid 검증 실패 - 빈 password)")
    void login_validationFail() throws Exception {
        // given - password가 빈 문자열
        LoginRequest request = new LoginRequest();
        ReflectionTestUtils.setField(request, "username", "testuser");
        ReflectionTestUtils.setField(request, "password", "");

        // when & then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

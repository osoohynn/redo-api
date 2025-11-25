package com.lsk.redoapi.review.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsk.redoapi.global.exception.CustomException;
import com.lsk.redoapi.global.exception.ErrorCode;
import com.lsk.redoapi.global.exception.GlobalExceptionHandler;
import com.lsk.redoapi.review.presentation.dto.request.CreateReviewRequest;
import com.lsk.redoapi.review.presentation.dto.request.UpdateReviewRequest;
import com.lsk.redoapi.review.presentation.dto.response.ReviewResponse;
import com.lsk.redoapi.review.service.ReviewService;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReviewController 테스트")
class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("POST /api/reviews - 리뷰 생성 성공")
    void createReview_success() throws Exception {
        // given
        CreateReviewRequest request = new CreateReviewRequest();
        ReflectionTestUtils.setField(request, "bookTitle", "테스트 책");
        ReflectionTestUtils.setField(request, "content", "테스트 내용");
        ReflectionTestUtils.setField(request, "rating", 5);

        ReviewResponse response = ReviewResponse.builder()
                .id(1L)
                .bookTitle("테스트 책")
                .content("테스트 내용")
                .rating(5)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(reviewService.createReview(any(CreateReviewRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.bookTitle").value("테스트 책"))
                .andExpect(jsonPath("$.data.rating").value(5));
    }

    @Test
    @DisplayName("POST /api/reviews - 실패 (@Valid 검증 실패 - 빈 제목)")
    void createReview_validationFail() throws Exception {
        // given - bookTitle이 빈 문자열
        CreateReviewRequest request = new CreateReviewRequest();
        ReflectionTestUtils.setField(request, "bookTitle", "");
        ReflectionTestUtils.setField(request, "content", "테스트 내용");
        ReflectionTestUtils.setField(request, "rating", 5);

        // when & then
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/reviews/{id} - 리뷰 조회 성공")
    void getReview_success() throws Exception {
        // given
        Long reviewId = 1L;
        ReviewResponse response = ReviewResponse.builder()
                .id(reviewId)
                .bookTitle("테스트 책")
                .content("테스트 내용")
                .rating(5)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(reviewService.getReviewById(reviewId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/reviews/{id}", reviewId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(reviewId))
                .andExpect(jsonPath("$.data.bookTitle").value("테스트 책"));
    }

    @Test
    @DisplayName("GET /api/reviews/{id} - 실패 (존재하지 않는 리뷰)")
    void getReview_notFound() throws Exception {
        // given
        Long reviewId = 999L;
        given(reviewService.getReviewById(reviewId))
                .willThrow(new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        // when & then
        mockMvc.perform(get("/api/reviews/{id}", reviewId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/reviews/{id} - 리뷰 수정 성공")
    void updateReview_success() throws Exception {
        // given
        Long reviewId = 1L;
        UpdateReviewRequest request = new UpdateReviewRequest();
        ReflectionTestUtils.setField(request, "bookTitle", "수정된 책");
        ReflectionTestUtils.setField(request, "content", "수정된 내용");
        ReflectionTestUtils.setField(request, "rating", 4);

        ReviewResponse response = ReviewResponse.builder()
                .id(reviewId)
                .bookTitle("수정된 책")
                .content("수정된 내용")
                .rating(4)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(reviewService.updateReview(eq(reviewId), any(UpdateReviewRequest.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(put("/api/reviews/{id}", reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.bookTitle").value("수정된 책"))
                .andExpect(jsonPath("$.data.rating").value(4));
    }

    @Test
    @DisplayName("PUT /api/reviews/{id} - 실패 (@Valid 검증 실패 - 잘못된 평점)")
    void updateReview_validationFail() throws Exception {
        // given - rating이 범위 밖 (6점)
        Long reviewId = 1L;
        UpdateReviewRequest request = new UpdateReviewRequest();
        ReflectionTestUtils.setField(request, "bookTitle", "수정된 책");
        ReflectionTestUtils.setField(request, "content", "수정된 내용");
        ReflectionTestUtils.setField(request, "rating", 6); // 1~5 범위 초과

        // when & then
        mockMvc.perform(put("/api/reviews/{id}", reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

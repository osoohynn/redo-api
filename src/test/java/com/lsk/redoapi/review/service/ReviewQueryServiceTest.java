package com.lsk.redoapi.review.service;

import com.lsk.redoapi.global.exception.CustomException;
import com.lsk.redoapi.global.exception.ErrorCode;
import com.lsk.redoapi.review.domain.entity.ReviewEntity;
import com.lsk.redoapi.review.domain.repository.ReviewQueryRepository;
import com.lsk.redoapi.review.presentation.dto.response.ReviewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReviewQueryService 테스트")
class ReviewQueryServiceTest {

    @Mock
    private ReviewQueryRepository reviewQueryRepository;

    @InjectMocks
    private ReviewQueryServiceImpl reviewQueryService;

    @Test
    @DisplayName("리뷰 검색 - 성공")
    void searchReviews_success() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        ReviewEntity review = ReviewEntity.builder()
                .bookTitle("자바의 정석")
                .content("좋은 책입니다")
                .rating(5)
                .userId(1L)
                .build();
        ReflectionTestUtils.setField(review, "id", 1L);
        Page<ReviewEntity> reviewPage = new PageImpl<>(List.of(review));

        given(reviewQueryRepository.searchReviews(anyString(), anyInt(), anyInt(), anyLong(), any(Pageable.class)))
                .willReturn(reviewPage);

        // when
        Page<ReviewResponse> result = reviewQueryService.searchReviews("자바", 4, 5, 1L, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getBookTitle()).isEqualTo("자바의 정석");
    }

    @Test
    @DisplayName("리뷰 검색 - 실패: minRating이 1보다 작음")
    void searchReviews_fail_minRatingTooLow() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        assertThatThrownBy(() -> reviewQueryService.searchReviews(null, 0, null, null, pageable))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.INVALID_RATING_RANGE.getMessage());
    }

    @Test
    @DisplayName("리뷰 검색 - 실패: minRating이 5보다 큼")
    void searchReviews_fail_minRatingTooHigh() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        assertThatThrownBy(() -> reviewQueryService.searchReviews(null, 6, null, null, pageable))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.INVALID_RATING_RANGE.getMessage());
    }

    @Test
    @DisplayName("리뷰 검색 - 실패: maxRating이 1보다 작음")
    void searchReviews_fail_maxRatingTooLow() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        assertThatThrownBy(() -> reviewQueryService.searchReviews(null, null, 0, null, pageable))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.INVALID_RATING_RANGE.getMessage());
    }

    @Test
    @DisplayName("리뷰 검색 - 실패: maxRating이 5보다 큼")
    void searchReviews_fail_maxRatingTooHigh() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        assertThatThrownBy(() -> reviewQueryService.searchReviews(null, null, 6, null, pageable))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.INVALID_RATING_RANGE.getMessage());
    }

    @Test
    @DisplayName("리뷰 검색 - 실패: minRating이 maxRating보다 큼")
    void searchReviews_fail_minRatingGreaterThanMaxRating() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        assertThatThrownBy(() -> reviewQueryService.searchReviews(null, 5, 1, null, pageable))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.INVALID_RATING_RANGE.getMessage());
    }
}

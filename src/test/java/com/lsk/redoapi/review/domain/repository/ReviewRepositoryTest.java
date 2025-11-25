package com.lsk.redoapi.review.domain.repository;

import com.lsk.redoapi.review.domain.entity.ReviewEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ReviewRepository 테스트")
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 저장 - 성공")
    void save_success() {
        // given
        ReviewEntity review = ReviewEntity.builder()
                .bookTitle("테스트 책")
                .content("테스트 내용")
                .rating(5)
                .userId(1L)
                .build();

        // when
        ReviewEntity savedReview = reviewRepository.save(review);

        // then
        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getId()).isNotNull();
        assertThat(savedReview.getBookTitle()).isEqualTo("테스트 책");
    }

    @Test
    @DisplayName("리뷰 조회 by ID - 성공")
    void findById_success() {
        // given
        ReviewEntity review = ReviewEntity.builder()
                .bookTitle("자바의 정석")
                .content("좋은 책")
                .rating(5)
                .userId(1L)
                .build();
        ReviewEntity savedReview = reviewRepository.save(review);

        // when
        Optional<ReviewEntity> foundReview = reviewRepository.findById(savedReview.getId());

        // then
        assertThat(foundReview).isPresent();
        assertThat(foundReview.get().getBookTitle()).isEqualTo("자바의 정석");
    }

    @Test
    @DisplayName("리뷰 조회 by ID - 실패 (존재하지 않음)")
    void findById_notFound() {
        // given
        Long nonExistentId = 999L;

        // when
        Optional<ReviewEntity> foundReview = reviewRepository.findById(nonExistentId);

        // then
        assertThat(foundReview).isEmpty();
    }

    @Test
    @DisplayName("전체 리뷰 조회 - 성공")
    void findAll_success() {
        // given
        reviewRepository.save(ReviewEntity.builder()
                .bookTitle("책1").content("내용1").rating(5).userId(1L).build());
        reviewRepository.save(ReviewEntity.builder()
                .bookTitle("책2").content("내용2").rating(4).userId(1L).build());
        reviewRepository.save(ReviewEntity.builder()
                .bookTitle("책3").content("내용3").rating(3).userId(1L).build());

        // when
        List<ReviewEntity> reviews = reviewRepository.findAll();

        // then
        assertThat(reviews).hasSize(3);
        assertThat(reviews).extracting("bookTitle")
                .contains("책1", "책2", "책3");
    }

    @Test
    @DisplayName("리뷰 삭제 - 성공")
    void delete_success() {
        // given
        ReviewEntity review = reviewRepository.save(ReviewEntity.builder()
                .bookTitle("삭제할 책").content("내용").rating(5).userId(1L).build());
        Long savedId = review.getId();

        // when
        reviewRepository.deleteById(savedId);

        // then
        assertThat(reviewRepository.findById(savedId)).isEmpty();
    }

    @Test
    @DisplayName("리뷰 저장 - 실패 (필수 필드 누락 - bookTitle)")
    void save_fail_missingBookTitle() {
        // given - bookTitle이 null
        ReviewEntity review = ReviewEntity.builder()
                .bookTitle(null) // 필수 필드 누락
                .content("테스트 내용")
                .rating(5)
                .userId(1L)
                .build();

        // when & then
        try {
            reviewRepository.saveAndFlush(review);
            assertThat(true).isFalse(); // 예외가 발생해야 함
        } catch (Exception e) {
            assertThat(e).isNotNull();
        }
    }

    @Test
    @DisplayName("리뷰 저장 - 실패 (필수 필드 누락 - content)")
    void save_fail_missingContent() {
        // given - content가 null
        ReviewEntity review = ReviewEntity.builder()
                .bookTitle("테스트 책")
                .content(null) // 필수 필드 누락
                .rating(5)
                .userId(1L)
                .build();

        // when & then
        try {
            reviewRepository.saveAndFlush(review);
            assertThat(true).isFalse(); // 예외가 발생해야 함
        } catch (Exception e) {
            assertThat(e).isNotNull();
        }
    }

    @Test
    @DisplayName("리뷰 저장 - 실패 (필수 필드 누락 - userId)")
    void save_fail_missingUserId() {
        // given - userId가 null
        ReviewEntity review = ReviewEntity.builder()
                .bookTitle("테스트 책")
                .content("테스트 내용")
                .rating(5)
                .userId(null) // 필수 필드 누락
                .build();

        // when & then
        try {
            reviewRepository.saveAndFlush(review);
            assertThat(true).isFalse(); // 예외가 발생해야 함
        } catch (Exception e) {
            assertThat(e).isNotNull();
        }
    }
}

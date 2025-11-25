package com.lsk.redoapi.review.domain.repository;

import com.lsk.redoapi.review.domain.entity.ReviewEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ReviewQueryRepositoryImpl.class, com.lsk.redoapi.global.config.QueryDslConfig.class})
@Sql("/data.sql")
@DisplayName("ReviewQueryRepository 테스트")
class ReviewQueryRepositoryTest {

    @Autowired
    private ReviewQueryRepository reviewQueryRepository;

    @Test
    @DisplayName("책 제목으로 리뷰 검색 - 성공")
    void searchReviews_byBookTitle() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when - "자바" 키워드로 검색
        Page<ReviewEntity> result = reviewQueryRepository.searchReviews("자바", null, null, null, pageable);

        // then
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent()).allMatch(review ->
            review.getBookTitle().contains("자바")
        );
    }

    @Test
    @DisplayName("평점 범위로 리뷰 검색 - 성공")
    void searchReviews_byRatingRange() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when - 평점 4~5점인 리뷰 검색
        Page<ReviewEntity> result = reviewQueryRepository.searchReviews(null, 4, 5, null, pageable);

        // then
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent()).allMatch(review ->
            review.getRating() >= 4 && review.getRating() <= 5
        );
    }

    @Test
    @DisplayName("사용자ID로 리뷰 검색 - 성공")
    void searchReviews_byUserId() {
        // given
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<ReviewEntity> result = reviewQueryRepository.searchReviews(null, null, null, userId, pageable);

        // then
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent()).allMatch(review -> review.getUserId().equals(userId));
    }

    @Test
    @DisplayName("복합 조건으로 리뷰 검색 - 성공")
    void searchReviews_multipleConditions() {
        // given
        String bookTitle = "스프링";
        Integer minRating = 4;
        Long userId = 2L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<ReviewEntity> result = reviewQueryRepository.searchReviews(bookTitle, minRating, null, userId, pageable);

        // then
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent()).allMatch(review ->
            review.getBookTitle().contains(bookTitle) &&
            review.getRating() >= minRating &&
            review.getUserId().equals(userId)
        );
    }

    @Test
    @DisplayName("페이징 처리 - 성공")
    void searchReviews_withPaging() {
        // given
        Pageable pageable = PageRequest.of(0, 5); // 첫 페이지, 5개씩

        // when
        Page<ReviewEntity> result = reviewQueryRepository.searchReviews(null, null, null, null, pageable);

        // then
        assertThat(result.getContent()).hasSize(5);
        assertThat(result.getTotalElements()).isEqualTo(15); // data.sql의 전체 리뷰 수
        assertThat(result.getTotalPages()).isEqualTo(3); // 15개를 5개씩 = 3페이지
    }

    @Test
    @DisplayName("정렬 처리 - 평점 내림차순")
    void searchReviews_withSorting() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "rating"));

        // when
        Page<ReviewEntity> result = reviewQueryRepository.searchReviews(null, null, null, null, pageable);

        // then
        assertThat(result.getContent()).isNotEmpty();
        // 평점이 높은 순으로 정렬되어 있는지 확인
        for (int i = 0; i < result.getContent().size() - 1; i++) {
            assertThat(result.getContent().get(i).getRating())
                .isGreaterThanOrEqualTo(result.getContent().get(i + 1).getRating());
        }
    }
}

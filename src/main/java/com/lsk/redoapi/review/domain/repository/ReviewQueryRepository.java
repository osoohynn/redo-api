package com.lsk.redoapi.review.domain.repository;

import com.lsk.redoapi.review.domain.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryRepository {

    /**
     * 다양한 조건으로 리뷰 목록 조회
     * @param bookTitle 책 제목 (부분 검색)
     * @param minRating 최소 평점
     * @param maxRating 최대 평점
     * @param userId 사용자 ID
     * @param pageable 페이징 정보
     * @return 페이징된 리뷰 목록
     */
    Page<ReviewEntity> searchReviews(String bookTitle, Integer minRating, Integer maxRating,
                                      Long userId, Pageable pageable);
}
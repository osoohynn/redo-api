package com.lsk.redoapi.review.service;

import com.lsk.redoapi.review.presentation.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryService {

    Page<ReviewResponse> searchReviews(String bookTitle, Integer minRating, Integer maxRating,
                                       Long userId, Pageable pageable);

    Page<ReviewResponse> getReviewsByRating(int rating, Pageable pageable);

    Page<ReviewResponse> getReviewsByUser(Long userId, Pageable pageable);

    Page<ReviewResponse> getReviewsByBookTitle(String bookTitle, Pageable pageable);

    Page<ReviewResponse> getHighRatedReviews(Pageable pageable);
}

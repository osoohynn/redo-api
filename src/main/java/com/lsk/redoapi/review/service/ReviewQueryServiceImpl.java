package com.lsk.redoapi.review.service;

import com.lsk.redoapi.review.domain.entity.ReviewEntity;
import com.lsk.redoapi.review.domain.repository.ReviewQueryRepository;
import com.lsk.redoapi.review.presentation.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewQueryRepository reviewQueryRepository;

    @Override
    public Page<ReviewResponse> searchReviews(String bookTitle, Integer minRating, Integer maxRating,
                                               Long userId, Pageable pageable) {
        Page<ReviewEntity> reviews = reviewQueryRepository.searchReviews(
                bookTitle,
                minRating,
                maxRating,
                userId,
                pageable
        );

        return reviews.map(ReviewResponse::from);
    }

    @Override
    public Page<ReviewResponse> getReviewsByRating(int rating, Pageable pageable) {
        return searchReviews(null, rating, rating, null, pageable);
    }

    @Override
    public Page<ReviewResponse> getReviewsByUser(Long userId, Pageable pageable) {
        return searchReviews(null, null, null, userId, pageable);
    }

    @Override
    public Page<ReviewResponse> getReviewsByBookTitle(String bookTitle, Pageable pageable) {
        return searchReviews(bookTitle, null, null, null, pageable);
    }

    @Override
    public Page<ReviewResponse> getHighRatedReviews(Pageable pageable) {
        return searchReviews(null, 4, null, null, pageable);
    }
}

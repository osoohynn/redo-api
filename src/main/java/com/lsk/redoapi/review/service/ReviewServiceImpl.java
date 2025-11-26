package com.lsk.redoapi.review.service;

import com.lsk.redoapi.global.exception.CustomException;
import com.lsk.redoapi.global.exception.ErrorCode;
import com.lsk.redoapi.review.domain.entity.ReviewEntity;
import com.lsk.redoapi.review.domain.repository.ReviewRepository;
import com.lsk.redoapi.review.presentation.dto.request.CreateReviewRequest;
import com.lsk.redoapi.review.presentation.dto.request.UpdateReviewRequest;
import com.lsk.redoapi.review.presentation.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request) {
        Long userId = getCurrentUserId();

        ReviewEntity review = ReviewEntity.builder()
                .bookTitle(request.getBookTitle())
                .content(request.getContent())
                .rating(request.getRating())
                .userId(userId)
                .build();

        ReviewEntity savedReview = reviewRepository.save(review);
        return ReviewResponse.from(savedReview);
    }

    @Override
    public ReviewResponse getReviewById(Long id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        return ReviewResponse.from(review);
    }

    @Override
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(Long id, UpdateReviewRequest request) {
        Long userId = getCurrentUserId();

        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (review.isNotOwner(userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        review.update(request.getBookTitle(), request.getContent(), request.getRating());

        return ReviewResponse.from(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        Long userId = getCurrentUserId();

        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (review.isNotOwner(userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        reviewRepository.deleteById(id);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        return (Long) authentication.getDetails();
    }
}

package com.lsk.redoapi.review.service;

import com.lsk.redoapi.review.presentation.dto.request.CreateReviewRequest;
import com.lsk.redoapi.review.presentation.dto.request.UpdateReviewRequest;
import com.lsk.redoapi.review.presentation.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(CreateReviewRequest request);

    ReviewResponse getReviewById(Long id);

    List<ReviewResponse> getAllReviews();

    ReviewResponse updateReview(Long id, UpdateReviewRequest request);

    void deleteReview(Long id);
}

package com.lsk.redoapi.review.presentation;

import com.lsk.redoapi.global.common.BaseResponse;
import com.lsk.redoapi.review.presentation.dto.request.CreateReviewRequest;
import com.lsk.redoapi.review.presentation.dto.request.UpdateReviewRequest;
import com.lsk.redoapi.review.presentation.dto.response.ReviewResponse;
import com.lsk.redoapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public BaseResponse<ReviewResponse> createReview(@RequestBody CreateReviewRequest request) {
        ReviewResponse response = reviewService.createReview(request);
        return BaseResponse.created(response);
    }

    @GetMapping("/{id}")
    public BaseResponse<ReviewResponse> getReview(@PathVariable Long id) {
        ReviewResponse response = reviewService.getReviewById(id);
        return BaseResponse.ok(response);
    }

    @GetMapping
    public BaseResponse<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> responses = reviewService.getAllReviews();
        return BaseResponse.ok(responses);
    }

    @PutMapping("/{id}")
    public BaseResponse<ReviewResponse> updateReview(
            @PathVariable Long id,
            @RequestBody UpdateReviewRequest request) {
        ReviewResponse response = reviewService.updateReview(id, request);
        return BaseResponse.ok(response);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return BaseResponse.noContent();
    }
}

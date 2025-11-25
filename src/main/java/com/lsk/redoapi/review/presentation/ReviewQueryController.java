package com.lsk.redoapi.review.presentation;

import com.lsk.redoapi.review.presentation.dto.response.ReviewResponse;
import com.lsk.redoapi.review.service.ReviewQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewQueryController {

    private final ReviewQueryService reviewQueryService;

    @GetMapping("/search")
    public ResponseEntity<Page<ReviewResponse>> searchReviews(
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(required = false) Long userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ReviewResponse> reviews = reviewQueryService.searchReviews(
                bookTitle, minRating, maxRating, userId, pageable
        );
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<Page<ReviewResponse>> getReviewsByRating(
            @PathVariable int rating,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ReviewResponse> reviews = reviewQueryService.getReviewsByRating(rating, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewsByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ReviewResponse> reviews = reviewQueryService.getReviewsByUser(userId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/book")
    public ResponseEntity<Page<ReviewResponse>> getReviewsByBookTitle(
            @RequestParam String bookTitle,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ReviewResponse> reviews = reviewQueryService.getReviewsByBookTitle(bookTitle, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/high-rated")
    public ResponseEntity<Page<ReviewResponse>> getHighRatedReviews(
            @PageableDefault(size = 20, sort = "rating", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ReviewResponse> reviews = reviewQueryService.getHighRatedReviews(pageable);
        return ResponseEntity.ok(reviews);
    }
}
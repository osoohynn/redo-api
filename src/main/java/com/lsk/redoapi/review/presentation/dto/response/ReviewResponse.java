package com.lsk.redoapi.review.presentation.dto.response;

import com.lsk.redoapi.review.domain.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {

    private Long id;
    private String bookTitle;
    private String content;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReviewResponse from(ReviewEntity entity) {
        return ReviewResponse.builder()
                .id(entity.getId())
                .bookTitle(entity.getBookTitle())
                .content(entity.getContent())
                .rating(entity.getRating())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
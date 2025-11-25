package com.lsk.redoapi.review.presentation.dto.request;

import lombok.Getter;

@Getter
public class UpdateReviewRequest {

    private String bookTitle;
    private String content;
    private Integer rating;
}

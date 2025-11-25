package com.lsk.redoapi.review.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class CreateReviewRequest {

    @NotBlank(message = "책 제목은 필수입니다.")
    @Size(min = 1, max = 200, message = "책 제목은 1자 이상 200자 이하여야 합니다.")
    private String bookTitle;

    @NotBlank(message = "리뷰 내용은 필수입니다.")
    @Size(min = 1, max = 5000, message = "리뷰 내용은 1자 이상 5000자 이하여야 합니다.")
    private String content;

    @NotNull(message = "평점은 필수입니다.")
    @Min(value = 1, message = "평점은 1 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5 이하여야 합니다.")
    private Integer rating;
}

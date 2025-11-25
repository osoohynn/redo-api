package com.lsk.redoapi.review.domain.entity;

import com.lsk.redoapi.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "책 제목은 필수입니다.")
    @Size(min = 1, max = 200, message = "책 제목은 1자 이상 200자 이하여야 합니다.")
    @Column(nullable = false, length = 200)
    private String bookTitle;

    @NotBlank(message = "리뷰 내용은 필수입니다.")
    @Size(min = 1, max = 5000, message = "리뷰 내용은 1자 이상 5000자 이하여야 합니다.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Min(value = 1, message = "평점은 1 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5 이하여야 합니다.")
    @Column(nullable = false)
    private int rating;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public ReviewEntity(String bookTitle, String content, int rating, Long userId) {
        this.bookTitle = bookTitle;
        this.content = content;
        this.rating = rating;
        this.userId = userId;
    }

    public void update(String bookTitle, String content, int rating) {
        this.bookTitle = bookTitle;
        this.content = content;
        this.rating = rating;
    }

    public boolean isNotOwner(Long userId) {
        return !this.userId.equals(userId);
    }
}

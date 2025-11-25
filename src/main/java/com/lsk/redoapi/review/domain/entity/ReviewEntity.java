package com.lsk.redoapi.review.domain.entity;

import com.lsk.redoapi.global.common.BaseEntity;
import jakarta.persistence.*;
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

    @Column(nullable = false, length = 200)
    private String bookTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

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

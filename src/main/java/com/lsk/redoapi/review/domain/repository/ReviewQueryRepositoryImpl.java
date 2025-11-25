package com.lsk.redoapi.review.domain.repository;

import com.lsk.redoapi.review.domain.entity.QReviewEntity;
import com.lsk.redoapi.review.domain.entity.ReviewEntity;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QReviewEntity review = QReviewEntity.reviewEntity;

    @Override
    public Page<ReviewEntity> searchReviews(String bookTitle, Integer minRating, Integer maxRating,
                                             Long userId, Pageable pageable) {
        List<ReviewEntity> content = queryFactory
                .selectFrom(review)
                .where(
                        bookTitleContains(bookTitle),
                        ratingGoe(minRating),
                        ratingLoe(maxRating),
                        userIdEq(userId)
                )
                .orderBy(getOrderSpecifiers(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        bookTitleContains(bookTitle),
                        ratingGoe(minRating),
                        ratingLoe(maxRating),
                        userIdEq(userId)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression bookTitleContains(String bookTitle) {
        return bookTitle != null ? review.bookTitle.containsIgnoreCase(bookTitle) : null;
    }

    private BooleanExpression ratingGoe(Integer minRating) {
        return minRating != null ? review.rating.goe(minRating) : null;
    }

    private BooleanExpression ratingLoe(Integer maxRating) {
        return maxRating != null ? review.rating.loe(maxRating) : null;
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? review.userId.eq(userId) : null;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (sort.isEmpty()) {
            orders.add(review.createdAt.desc());
            return orders.toArray(new OrderSpecifier[0]);
        }

        for (Sort.Order order : sort) {
            switch (order.getProperty()) {
                case "rating" -> orders.add(order.isAscending() ? review.rating.asc() : review.rating.desc());
                case "bookTitle" -> orders.add(order.isAscending() ? review.bookTitle.asc() : review.bookTitle.desc());
                case "createdAt" -> orders.add(order.isAscending() ? review.createdAt.asc() : review.createdAt.desc());
                case "updatedAt" -> orders.add(order.isAscending() ? review.updatedAt.asc() : review.updatedAt.desc());
                default -> orders.add(review.createdAt.desc());
            }
        }

        return orders.toArray(new OrderSpecifier[0]);
    }
}

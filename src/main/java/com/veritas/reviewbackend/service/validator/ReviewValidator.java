package com.veritas.reviewbackend.service.validator;

import com.veritas.reviewbackend.dto.ReviewRequest;
import com.veritas.reviewbackend.exception.CustomException;
import com.veritas.reviewbackend.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ReviewValidator {

    public void validate(List<ReviewRequest> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "리뷰 요청은 비어 있을 수 없습니다.");
        }

        for (ReviewRequest review : reviews) {
            if (review == null) {
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "리뷰 요청에 null 항목이 포함되어 있습니다.");
            }
        }

        boolean hasValidReview = reviews.stream()
                .filter(Objects::nonNull) // 혹시라도 누락됐을 경우를 방지
                .anyMatch(r -> r.content() != null && !r.content().isBlank());

        if (!hasValidReview) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "내용이 있는 리뷰가 최소 1개 이상 필요합니다.");
        }
    }

}

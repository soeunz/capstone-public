package com.veritas.reviewbackend.service.validator;

import com.veritas.reviewbackend.dto.ReviewResponse;
import com.veritas.reviewbackend.exception.CustomException;
import com.veritas.reviewbackend.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MlResponseValidator {

    public void validate(List<ReviewResponse> responses) {
        if (responses == null || responses.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "ML 응답이 비어 있습니다.");
        }

        for (ReviewResponse response : responses) {
            if (response == null) {
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "ML 응답에 null 값이 포함되어 있습니다.");
            }

            if (response.reviewId() == null || response.reviewId().isBlank()) {
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "리뷰 ID는 null이거나 빈 값일 수 없습니다.");
            }

            if (response.score() < 0.0 || response.score() > 1.0) {
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "score 값은 0.0 이상 1.0 이하여야 합니다.");
            }
        }
    }

}


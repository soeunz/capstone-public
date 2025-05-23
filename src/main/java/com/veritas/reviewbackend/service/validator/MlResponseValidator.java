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
        }
    }

}


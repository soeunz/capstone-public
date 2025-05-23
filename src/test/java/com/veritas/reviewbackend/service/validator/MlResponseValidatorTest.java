package com.veritas.reviewbackend.service.validator;

import com.veritas.reviewbackend.dto.ReviewResponse;
import com.veritas.reviewbackend.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ML 응답 유효성 검사 테스트")
class MlResponseValidatorTest {

    private final MlResponseValidator validator = new MlResponseValidator();

    @Test
    void validResponses_pass() {
        List<ReviewResponse> responses = List.of(
                new ReviewResponse("r001", true),
                new ReviewResponse("r002", false)
        );

        assertDoesNotThrow(() -> validator.validate(responses));
    }

    @Test
    void nullReviewId_throws() {
        List<ReviewResponse> responses = List.of(
                new ReviewResponse(null, true)
        );

        assertThrows(CustomException.class, () -> validator.validate(responses));
    }

    @Test
    void blankReviewId_throws() {
        List<ReviewResponse> responses = List.of(
                new ReviewResponse("   ", false)
        );

        assertThrows(CustomException.class, () -> validator.validate(responses));
    }

    @Test
    void nullElementInList_throws() {
        List<ReviewResponse> responses = new ArrayList<>();
        responses.add(new ReviewResponse("r001", true));
        responses.add(null);

        assertThrows(CustomException.class, () -> validator.validate(responses));
    }

    @Test
    void nullList_throws() {
        assertThrows(CustomException.class, () -> validator.validate(null));
    }

    @Test
    void emptyList_throws() {
        assertThrows(CustomException.class, () -> validator.validate(List.of()));
    }
}

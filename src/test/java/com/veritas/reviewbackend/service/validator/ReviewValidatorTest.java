package com.veritas.reviewbackend.service.validator;

import com.veritas.reviewbackend.dto.ReviewRequest;
import com.veritas.reviewbackend.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("프론트 요청 유효성 검사 테스트")
class ReviewValidatorTest {

    private final ReviewValidator validator = new ReviewValidator();

    @Test
    void validReviews_pass() {
        List<ReviewRequest> reviews = List.of(
                new ReviewRequest("r001", 4, "좋아요", true),
                new ReviewRequest("r002", 5, "아주 만족", false)
        );

        assertDoesNotThrow(() -> validator.validate(reviews));
    }

    @Test
    void nullList_throws() {
        assertThrows(CustomException.class, () -> validator.validate(null));
    }

    @Test
    void emptyList_throws() {
        assertThrows(CustomException.class, () -> validator.validate(List.of()));
    }

    @Test
    void listWithNullElement_throws() {
        List<ReviewRequest> reviews = new ArrayList<>();
        reviews.add(null);
        assertThrows(CustomException.class, () -> validator.validate(reviews));
    }

    @Test
    void allBlankContents_throws() {
        List<ReviewRequest> reviews = List.of(
                new ReviewRequest("r001", 4, "", true),
                new ReviewRequest("r002", 3, "  ", false),
                new ReviewRequest("r003", 5, null, true)
        );

        assertThrows(CustomException.class, () -> validator.validate(reviews));
    }

    @Test
    void someBlankContents_pass() {
        List<ReviewRequest> reviews = List.of(
                new ReviewRequest("r001", 4, "", true),
                new ReviewRequest("r002", 3, "배송이 빨라요", false),
                new ReviewRequest("r003", 5, null, true)
        );

        assertDoesNotThrow(() -> validator.validate(reviews));
    }
}

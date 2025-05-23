package com.veritas.reviewbackend.controller;

import com.veritas.reviewbackend.dto.ReviewAnalyzeRequest;
import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import com.veritas.reviewbackend.dto.ReviewRequest;
import com.veritas.reviewbackend.exception.CustomException;
import com.veritas.reviewbackend.service.ReviewService;
import com.veritas.reviewbackend.service.validator.ReviewValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewValidator reviewValidator;

    @PostMapping("/analyze-reviews")
    public ResponseEntity<?> analyze(
            @RequestBody @Valid ReviewAnalyzeRequest request) {

        try {
            // 유효성 검사
            reviewValidator.validate(request.reviews());

            // content가 있는 리뷰만 선별
            List<ReviewRequest> validReviews = request.reviews().stream()
                    .filter(r -> r.content() != null && !r.content().isBlank())
                    .toList();

            // 분석 요청
            ReviewAnalyzeResponse response = reviewService.analyze(validReviews);
            return ResponseEntity.ok(response);

        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", Map.of(
                            "code", e.getErrorCode().getCode(),
                            "message", e.getMessage()
                    )));
        }
    }
}

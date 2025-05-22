package com.veritas.reviewbackend.controller;

import com.veritas.reviewbackend.dto.ApiResponse;
import com.veritas.reviewbackend.dto.ReviewAnalyzeRequest;
import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import com.veritas.reviewbackend.dto.ReviewRequest;
import com.veritas.reviewbackend.service.ReviewService;
import com.veritas.reviewbackend.service.validator.ReviewValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewValidator reviewValidator;

    @PostMapping("/analyze-reviews")
    public ResponseEntity<ApiResponse<ReviewAnalyzeResponse>> analyze(
            @RequestBody @Valid ReviewAnalyzeRequest request) {

        // 유효성 검사
        reviewValidator.validate(request.reviews());

        // content가 있는 리뷰만 선별
        List<ReviewRequest> validReviews = request.reviews().stream()
                .filter(r -> r.content() != null && !r.content().isBlank())
                .toList();

        // 분석 요청
        ReviewAnalyzeResponse response = reviewService.analyze(validReviews);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}

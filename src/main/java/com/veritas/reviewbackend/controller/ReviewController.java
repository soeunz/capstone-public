package com.veritas.reviewbackend.controller;

import com.veritas.reviewbackend.dto.ApiResponse;
import com.veritas.reviewbackend.dto.ReviewAnalyzeRequest;
import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import com.veritas.reviewbackend.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/analyze-reviews")
    public ResponseEntity<ApiResponse<ReviewAnalyzeResponse>> analyze(
            @RequestBody @Valid ReviewAnalyzeRequest request) {
        ReviewAnalyzeResponse response = reviewService.analyze(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}

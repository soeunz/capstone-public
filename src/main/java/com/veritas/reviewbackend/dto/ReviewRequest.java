package com.veritas.reviewbackend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(
        @NotBlank String reviewId,
        @Min(1) @Max(5) int rating,
        String content,
        boolean boolimg
) {}

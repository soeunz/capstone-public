package com.veritas.reviewbackend.dto;

public record ReviewResponse(
        String reviewId,
        boolean isAiGenerated
) {}

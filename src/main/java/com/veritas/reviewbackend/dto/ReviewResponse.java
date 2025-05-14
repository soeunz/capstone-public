package com.veritas.reviewbackend.dto;

public record ReviewResponse(
        String reviewId,
        String label,
        double confidence
) {}

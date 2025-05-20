package com.veritas.reviewbackend.dto;

public record ReviewRequest(
        String reviewId,
        int rating,
        String content,
        boolean boolimg
) {}

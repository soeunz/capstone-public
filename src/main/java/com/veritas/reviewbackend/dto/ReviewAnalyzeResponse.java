package com.veritas.reviewbackend.dto;

import java.util.List;

public record ReviewAnalyzeResponse(
        List<ReviewResponse> results
) {}

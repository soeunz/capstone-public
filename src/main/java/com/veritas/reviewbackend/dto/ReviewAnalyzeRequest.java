package com.veritas.reviewbackend.dto;

import java.util.List;

public record ReviewAnalyzeRequest(
        List<ReviewRequest> reviews
) {}

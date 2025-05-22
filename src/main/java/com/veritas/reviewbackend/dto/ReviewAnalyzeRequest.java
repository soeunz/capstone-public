package com.veritas.reviewbackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ReviewAnalyzeRequest(
        @NotNull
        @Size(min = 1)
        List<@Valid ReviewRequest> reviews
) {}

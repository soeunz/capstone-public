package com.veritas.reviewbackend.exception;

public record ApiError(
        String code,
        String message
) {
    public static ApiError from(ErrorCode errorCode) {
        return new ApiError(errorCode.getCode(), errorCode.getMessage());
    }
}

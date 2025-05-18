package com.veritas.reviewbackend.dto;

import com.veritas.reviewbackend.exception.ApiError;
import com.veritas.reviewbackend.exception.ErrorCode;

public record ApiResponse<T>(
        boolean success,
        T data,
        ApiError error
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    // ErrorCode를 사용해 일관된 에러 응답 생성 (코드/메시지 자동 설정)
    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(false, null, ApiError.from(errorCode));
    }

    // 코드와 메시지를 직접 지정해 실패 응답 생성 (enum에 없는 코드 처리)
    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(false, null, new ApiError(code, message));
    }

}

package com.veritas.reviewbackend.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 400 BAD REQUEST (클라이언트 입력 문제)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "INVALID_INPUT_VALUE", "입력값이 올바르지 않습니다."),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "MISSING_REQUEST_PARAMETER", "요청 파라미터가 누락되었습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "INVALID_TYPE_VALUE", "요청 파라미터 타입이 잘못되었습니다."),
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "JSON_PARSE_ERROR", "요청 형식이 잘못되었습니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.BAD_REQUEST, "UNSUPPORTED_MEDIA_TYPE", "지원하지 않는 미디어 타입입니다."),
    // 401 UNAUTHORIZED (인증 관련 문제)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "인증 정보가 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", "토큰이 만료되었습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "TOKEN_INVALID", "유효하지 않은 토큰입니다."),

    // 403 FORBIDDEN (권한 없음)
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "접근 권한이 없습니다."),

    // 404 NOT FOUND
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", "요청한 리소스를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    API_NOT_FOUND(HttpStatus.NOT_FOUND, "API_NOT_FOUND", "API 엔드포인트가 존재하지 않습니다."),

    // 405 METHOD NOT ALLOWED
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "지원하지 않는 요청 방식입니다."),

    // 409 CONFLICT (중복, 상태 충돌)
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "DUPLICATE_RESOURCE", "중복된 리소스입니다."),
    INVALID_STATE(HttpStatus.CONFLICT, "INVALID_STATE", "현재 상태에서 수행할 수 없는 요청입니다."),

    // 422 UNPROCESSABLE ENTITY
    VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, "VALIDATION_ERROR", "데이터 유효성 검증에 실패했습니다."),

    // 429 TOO MANY REQUESTS
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "TOO_MANY_REQUESTS", "요청이 너무 많습니다. 잠시 후 다시 시도하세요."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DATABASE_ERROR", "데이터베이스 오류가 발생했습니다."),
    REDIS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_ERROR", "캐시 서버(Redis) 오류가 발생했습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_UPLOAD_ERROR", "파일 업로드 중 오류가 발생했습니다."),
    FILE_DOWNLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_DOWNLOAD_ERROR", "파일 다운로드 중 오류가 발생했습니다."),
    FILE_PROCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_PROCESS_ERROR", "파일 처리 중 오류가 발생했습니다."),
    ML_TIMEOUT(HttpStatus.INTERNAL_SERVER_ERROR, "ML_TIMEOUT", "머신러닝 서버 응답이 지연되었습니다."),
    ML_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ML_PROCESSING_ERROR", "ML 처리 중 오류가 발생했습니다."),
    EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "EXTERNAL_API_ERROR", "외부 API 연동 중 오류가 발생했습니다."),
    CONFIGURATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CONFIGURATION_ERROR", "서버 설정 오류가 발생했습니다."),
    UNKNOWN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_SERVER_ERROR", "알 수 없는 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}

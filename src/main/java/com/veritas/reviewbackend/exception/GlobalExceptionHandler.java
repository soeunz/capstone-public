package com.veritas.reviewbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
        log.warn("[CustomException] {}", ex.getMessage());
        return buildErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<Map<String, Object>> handleBadRequest(Exception ex) {
        log.warn("[BadRequest] {}", ex.getMessage());

        if (ex instanceof MethodArgumentNotValidException) {
            return buildErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return buildErrorResponse(ErrorCode.INVALID_TYPE_VALUE);
        }
        if (ex instanceof MissingServletRequestParameterException) {
            return buildErrorResponse(ErrorCode.MISSING_REQUEST_PARAMETER);
        }
        if (ex instanceof HttpMessageNotReadableException) {
            return buildErrorResponse(ErrorCode.JSON_PARSE_ERROR);
        }

        return buildErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotAllowed(Exception ex) {
        log.warn("[MethodNotAllowed] {}", ex.getMessage());
        return buildErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnknownException(Exception ex) {
        log.error("[UnknownException] {}", ex.getMessage(), ex);
        return buildErrorResponse(ErrorCode.UNKNOWN_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(ErrorCode errorCode) {
        return buildErrorResponse(errorCode, errorCode.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(ErrorCode errorCode, String message) {
        Map<String, Object> errorBody = new HashMap<>();
        Map<String, String> error = new HashMap<>();
        error.put("code", errorCode.getCode());
        error.put("message", message);
        errorBody.put("error", error);

        return ResponseEntity.status(errorCode.getStatus())
                .body(errorBody);
    }
}


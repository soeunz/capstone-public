package com.veritas.reviewbackend.exception;

import com.veritas.reviewbackend.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
        log.warn("[CustomException] {}", ex.getMessage());
        return buildResponse(ex.getErrorCode());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception ex) {
        log.warn("[BadRequest] {}", ex.getMessage());

        if (ex instanceof MethodArgumentNotValidException) {
            return buildResponse(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return buildResponse(ErrorCode.INVALID_TYPE_VALUE);
        }
        if (ex instanceof MissingServletRequestParameterException) {
            return buildResponse(ErrorCode.MISSING_REQUEST_PARAMETER);
        }
        if (ex instanceof HttpMessageNotReadableException) {
            return buildResponse(ErrorCode.JSON_PARSE_ERROR);
        }

        return buildResponse(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(Exception ex) {
        log.warn("[MethodNotAllowed] {}", ex.getMessage());
        return buildResponse(ErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnknownException(Exception ex) {
        log.error("[UnknownException] {}", ex.getMessage(), ex);
        return buildResponse(ErrorCode.UNKNOWN_SERVER_ERROR);
    }

    private ResponseEntity<ApiResponse<Void>> buildResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode));
    }

}

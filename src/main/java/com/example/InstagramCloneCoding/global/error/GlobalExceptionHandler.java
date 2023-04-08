package com.example.InstagramCloneCoding.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    //패키지 구조 같은 거 숨기는 목적. 근데 개발 단계에서는 오류잡을 때 불편하므로 주석처리 해 놓았음
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception e) {
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponseDto makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponseDto.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
}

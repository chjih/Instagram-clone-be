package com.example.InstagramCloneCoding.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "forbidden"),
    ONLY_IMAGE_ALLOWED(HttpStatus.BAD_REQUEST, "only image are allowed for upload")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

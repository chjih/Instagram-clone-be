package com.example.InstagramCloneCoding.global.auth.error;

import com.example.InstagramCloneCoding.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "invalid access token"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "invalid refresh token"),
    WRONG_ID_PASSWORD(HttpStatus.BAD_REQUEST, "wrong id or password");

    private final HttpStatus httpStatus;
    private final String message;
}

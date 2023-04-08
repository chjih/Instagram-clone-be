package com.example.InstagramCloneCoding.domain.feed.error;

import com.example.InstagramCloneCoding.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "post not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

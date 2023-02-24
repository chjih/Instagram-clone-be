package com.example.InstagramCloneCoding.domain.post.error;

import com.example.InstagramCloneCoding.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements ErrorCode {

    POST_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "post not found"),
    ;

    private HttpStatus httpStatus;
    private String message;
}

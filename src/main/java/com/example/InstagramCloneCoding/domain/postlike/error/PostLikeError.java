package com.example.InstagramCloneCoding.domain.postlike.error;

import com.example.InstagramCloneCoding.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostLikeError implements ErrorCode {
    POST_LIKE_EXIST(HttpStatus.BAD_REQUEST, "already like post"),
    POST_LIKE_NOT_FIND(HttpStatus.BAD_REQUEST, "like not found");

    private final HttpStatus httpStatus;
    private final String message;
}

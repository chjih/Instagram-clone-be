package com.example.InstagramCloneCoding.domain.comment.error;

import com.example.InstagramCloneCoding.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    COMMENT_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "comment not found"),
    UNAVAILABLE_COMMENT_REQUEST(HttpStatus.BAD_REQUEST, "unavailable comment request"),
    ;

    private HttpStatus httpStatus;
    private String message;
}

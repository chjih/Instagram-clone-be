package com.example.InstagramCloneCoding.domain.follow.error;

import com.example.InstagramCloneCoding.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum FollowError implements ErrorCode {

    ALREADY_FOLLOWING(HttpStatus.BAD_REQUEST, "Already following."),
    NOT_FOLLOWING(HttpStatus.BAD_REQUEST, "Not following.");

    private final HttpStatus httpStatus;
    private final String message;
}

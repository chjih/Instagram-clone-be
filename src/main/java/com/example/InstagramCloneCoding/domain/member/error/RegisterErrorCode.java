package com.example.InstagramCloneCoding.domain.member.error;

import com.example.InstagramCloneCoding.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum RegisterErrorCode implements ErrorCode {

    ID_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "ID already exists"),
    EMAIL_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "Email is already registered"),
    WRONG_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "confirm password is wrong"),
    ;

    private HttpStatus httpStatus;
    private String message;
}

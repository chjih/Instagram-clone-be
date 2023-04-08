package com.example.InstagramCloneCoding.domain.member.error;

import com.example.InstagramCloneCoding.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    ID_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "id already exists"),
    EMAIL_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "email is already registered"),
    WRONG_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "confirm password is wrong"),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "token not found"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "member not found"),
    IMAGE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "fail to upload image");
    ;

    private HttpStatus httpStatus;
    private String message;
}

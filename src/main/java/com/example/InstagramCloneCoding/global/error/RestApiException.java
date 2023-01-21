package com.example.InstagramCloneCoding.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiException extends RuntimeException {

    private ErrorCode errorCode;
}

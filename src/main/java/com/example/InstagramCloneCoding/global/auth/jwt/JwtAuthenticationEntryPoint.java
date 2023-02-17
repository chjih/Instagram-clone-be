package com.example.InstagramCloneCoding.global.auth.jwt;

import com.example.InstagramCloneCoding.global.error.ErrorCode;
import com.example.InstagramCloneCoding.global.error.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.InstagramCloneCoding.global.auth.error.AuthErrorCode.INVALID_ACCESS_TOKEN;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 유효한 자격증명을 제공하지 않고 접근 -> 401에러
        ErrorCode errorCode = INVALID_ACCESS_TOKEN;

        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponseDto));
    }
}
package com.example.InstagramCloneCoding.global.auth;

import com.example.InstagramCloneCoding.global.auth.dto.LoginRequestDto;
import com.example.InstagramCloneCoding.global.auth.dto.TokenDto;
import com.example.InstagramCloneCoding.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthService authService;

    @PostMapping("login")
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto.getUserIdOrEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("test")
    public String test() {
        return "login success";
    }
}
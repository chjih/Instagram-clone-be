package com.example.InstagramCloneCoding.global.auth.api;

import com.example.InstagramCloneCoding.global.auth.dto.LoginRequestDto;
import com.example.InstagramCloneCoding.global.auth.dto.TokenDto;
import com.example.InstagramCloneCoding.global.auth.service.AuthService;
import com.example.InstagramCloneCoding.global.auth.service.HeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthService authService;
    private final HeaderService headerService;

    @PostMapping("signin")
    public TokenDto signIn(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.signIn(loginRequestDto.getUserIdOrEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("reissue")
    public TokenDto reissue(@RequestHeader("Authorization") String authorization,
                            @RequestHeader("RefreshToken") String refreshToken) {
        return authService.reissue(headerService.getAccessToken(authorization), refreshToken);
    }

    @PostMapping("signout")
    public String signOut(@RequestHeader("Authorization") String authorization) {
        if (authService.signOut(headerService.getAccessToken(authorization)))
            return "logout Success!";
        return "fail";
    }

    @PostMapping("test")
    public String test() {
        return "login success";
    }
}
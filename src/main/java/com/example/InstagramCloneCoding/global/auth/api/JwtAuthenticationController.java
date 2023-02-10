package com.example.InstagramCloneCoding.global.auth.api;

import com.example.InstagramCloneCoding.global.auth.dto.LoginRequestDto;
import com.example.InstagramCloneCoding.global.auth.dto.TokenDto;
import com.example.InstagramCloneCoding.global.auth.service.AuthService;
import com.example.InstagramCloneCoding.global.auth.service.HeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TokenDto> signIn(@RequestBody LoginRequestDto loginRequestDto) {
        TokenDto tokenDto = authService.signIn(loginRequestDto.getUserIdOrEmail(), loginRequestDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .body(tokenDto);
    }

    @PostMapping("reissue")
    public ResponseEntity<TokenDto> reissue(@RequestHeader("Authorization") String authorization,
                                            @RequestHeader("RefreshToken") String refreshToken) {
        TokenDto tokenDto = authService.reissue(headerService.getAccessToken(authorization), refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tokenDto);
    }

    @PostMapping("signout")
    public ResponseEntity<String> signOut(@RequestHeader("Authorization") String authorization) {
        authService.signOut(headerService.getAccessToken(authorization));
        return ResponseEntity.status(HttpStatus.OK)
                .body("signout success");
    }

    @PostMapping("test")
    public String test() {
        return "login success";
    }
}
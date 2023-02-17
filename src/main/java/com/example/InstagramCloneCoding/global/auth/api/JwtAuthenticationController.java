package com.example.InstagramCloneCoding.global.auth.api;

import com.example.InstagramCloneCoding.global.auth.dto.LoginRequestDto;
import com.example.InstagramCloneCoding.global.auth.dto.TokenDto;
import com.example.InstagramCloneCoding.global.auth.service.AuthService;
import com.example.InstagramCloneCoding.global.auth.service.HeaderService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
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
    public ResponseEntity<TokenDto> reissue(@Parameter(hidden = true) @RequestHeader("Authorization") String authorization,
                                            @RequestHeader("RefreshToken") String refreshToken) {
        TokenDto tokenDto = authService.reissue(headerService.getAccessToken(authorization), refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tokenDto);
    }

    @PostMapping("signout")
    public ResponseEntity<String> signOut(@Parameter(hidden = true) @RequestHeader("Authorization") String authorization) {
        authService.signOut(headerService.getAccessToken(authorization));
        return ResponseEntity.status(HttpStatus.OK)
                .body("signout success");
    }
}
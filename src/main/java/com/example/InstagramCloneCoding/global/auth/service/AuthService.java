package com.example.InstagramCloneCoding.global.auth.service;

import com.example.InstagramCloneCoding.global.auth.JwtTokenProvider;
import com.example.InstagramCloneCoding.global.auth.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenDto login(String id, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        try {
            Authentication authentication = authenticationManagerBuilder.getObject()
                    .authenticate(authenticationToken); // 실제 검증
            return jwtTokenProvider.generateToken(authentication);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

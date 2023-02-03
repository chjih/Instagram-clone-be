package com.example.InstagramCloneCoding.global.auth.service;

import com.example.InstagramCloneCoding.global.auth.dto.TokenDto;
import com.example.InstagramCloneCoding.global.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public TokenDto signIn(String id, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken); // 실제 검증

        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        setRedis(authentication.getName(), tokenDto); // Refresh Token 을 Redis 에 저장 - 만료 시간 설정을 통해 자동 삭제 처리
        return tokenDto;
    }

    public TokenDto reissue(String accessToken, String refreshToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        if (!refreshToken.equals(redisTemplate.opsForValue().get("RT:" + authentication.getName()))) {
            return null;
        }

        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        setRedis(authentication.getName(), tokenDto);
        return tokenDto;
    }

    public boolean signOut(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            return false;
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        if (redisTemplate.opsForValue().get(authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName()); // refresh token 삭제
        }

        redisTemplate.opsForValue().set(accessToken, "logout",
                jwtTokenProvider.getExpiration(accessToken) - (new Date()).getTime(),
                TimeUnit.MILLISECONDS); // access token 남은 시간 동안 blacklist 에 올림

        return true;
    }

    private void setRedis(String key, TokenDto tokenDto) {
        redisTemplate.opsForValue().set(
                "RT:" + key, tokenDto.getRefreshToken(),
                tokenDto.getRefreshTokenExpiresIn() - (new Date()).getTime(),
                TimeUnit.MILLISECONDS
        );
    }
}

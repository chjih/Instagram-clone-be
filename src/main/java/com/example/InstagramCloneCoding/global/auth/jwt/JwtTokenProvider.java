package com.example.InstagramCloneCoding.global.auth.jwt;

import com.example.InstagramCloneCoding.global.auth.dto.TokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.access-token-validate-in-seconds}")
    private long accessTokenValidateTime;
    @Value("${jwt.refresh-token-validate-in-seconds}")
    private long refreshTokenValidateTime;
    public static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORITIES_KEY = "auth";

    @Autowired
    private final JwtUserDetailsService jwtUserDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // secretKey 를 Base64로 인코딩
        accessTokenValidateTime *= 1000;
        refreshTokenValidateTime *= 1000;
    }

    public TokenDto generateToken(Authentication authentication) {
        long now = (new Date()).getTime();
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))        // payload "auth": "ROLE_USER"
                .setExpiration(new Date(now + accessTokenValidateTime))        // payload "exp": 1516239022 (예시)
                .signWith(SignatureAlgorithm.HS256, secretKey)    // header "alg": "HS512"
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + accessTokenValidateTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .accessTokenExpiresIn(now + accessTokenValidateTime)
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(now + refreshTokenValidateTime)
                .id(authentication.getName())
                .build();
    }

    // Jwt 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //UserDetails principal = new User(claims.getSubject(), "", authorities);
        UserDetails principal = jwtUserDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public long getExpiration(String accessToken) {
        return parseClaims(accessToken).getExpiration().getTime();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰의 유효성, 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("expired");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}

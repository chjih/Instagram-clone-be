package com.example.InstagramCloneCoding.global.config;

import com.example.InstagramCloneCoding.global.auth.jwt.JwtAuthenticationEntryPoint;
import com.example.InstagramCloneCoding.global.auth.jwt.JwtAuthenticationFilter;
import com.example.InstagramCloneCoding.global.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String[] SWAGGER_LIST={
            // swagger 접근 허용
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()        // cors 방지
                .csrf().disable()        // csrf 방지
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용x(jwt 사용)
                .and()
                .authorizeRequests()
                .antMatchers("/signin").permitAll() // 모든 요청 허가
                .antMatchers("/reissue").permitAll()
                .antMatchers("/accounts/emailsignup").permitAll()
                .antMatchers("/accounts/confirm-email").permitAll()
                .antMatchers(SWAGGER_LIST).permitAll()
                .anyRequest().authenticated()   // 나머지 인증 필요
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate)
                        , UsernamePasswordAuthenticationFilter.class)   // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣음
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.example.InstagramCloneCoding.global.config;

import com.example.InstagramCloneCoding.global.common.resolver.LoggedInUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoggedInUserArgumentResolver loggedInUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loggedInUserArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "DELETE", "PUT");
    }
}

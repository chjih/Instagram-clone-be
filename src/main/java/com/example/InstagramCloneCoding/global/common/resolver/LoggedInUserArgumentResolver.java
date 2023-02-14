package com.example.InstagramCloneCoding.global.common.resolver;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.auth.jwt.JwtUserDetails;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoggedInUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 호출되는 컨트롤러 파라미터의 어노테이션과 타입 검사
        return parameter.getParameterAnnotation(LoggedInUser.class) != null
                && parameter.getParameterType().equals(Member.class);
    }

    // supportsParameter 콜백 함수에서 true를 반환했을 경우 실행
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        JwtUserDetails userDetails = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            userDetails = (JwtUserDetails) authentication.getPrincipal();
            return userDetails.getMember();
        }
        else
            return null;
    }
}

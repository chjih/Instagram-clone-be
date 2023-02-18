package com.example.InstagramCloneCoding.global.auth.jwt;

import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdOrEmail) throws UsernameNotFoundException {
        return memberRepository.findByUserIdOrEmail(userIdOrEmail, userIdOrEmail)
                .map(JwtUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or id."));
    }
}

package com.example.InstagramCloneCoding.global.auth.jwt;

import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdOrEmail) throws UsernameNotFoundException {
        return memberRepository.findByUserIdOrEmail(userIdOrEmail, userIdOrEmail)
                .map(JwtUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or id."));
    }
}

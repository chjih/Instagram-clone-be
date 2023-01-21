package com.example.InstagramCloneCoding.domain.member.application;

import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import com.example.InstagramCloneCoding.domain.member.error.RegisterErrorCode;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberRegisterService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(MemberRegisterDto registerDto) {
        // 아이디 중복 확인
        Member member = memberRepository.findById(registerDto.getUserId()).orElse(null);
        if (member != null) {
            throw new RestApiException(RegisterErrorCode.ID_ALREADY_EXISTS);
        }

        // 이메일 중복 확인
        member = memberRepository.findByEmail(registerDto.getEmail()).orElse(null);
        if (member != null) {
            throw new RestApiException(RegisterErrorCode.EMAIL_ALREADY_REGISTERED);
        }

        // 비밀번호 확인
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new RestApiException(RegisterErrorCode.WRONG_CONFIRM_PASSWORD);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        // 저장
        member = new Member(registerDto.getEmail(), registerDto.getUserId(), encodedPassword);
        return memberRepository.save(member);
    }
}


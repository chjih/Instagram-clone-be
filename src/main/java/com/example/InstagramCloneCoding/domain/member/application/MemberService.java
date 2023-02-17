package com.example.InstagramCloneCoding.domain.member.application;

import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member register(MemberRegisterDto registerDto) {
        // 아이디 중복 확인
        Member member = memberRepository.findById(registerDto.getUserId()).orElse(null);
        if (member != null) {
            throw new RestApiException(MemberErrorCode.ID_ALREADY_EXISTS);
        }

        // 이메일 중복 확인
        member = memberRepository.findByEmail(registerDto.getEmail()).orElse(null);
        if (member != null) {
            throw new RestApiException(MemberErrorCode.EMAIL_ALREADY_REGISTERED);
        }

        // 비밀번호 확인
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new RestApiException(MemberErrorCode.WRONG_CONFIRM_PASSWORD);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        // 저장
        member = new Member(registerDto.getEmail(), registerDto.getUserId(), registerDto.getName(), encodedPassword);
        return memberRepository.save(member);
    }

    public void changeProfileImage(String userId, String imagePath) {
        // 멤버 가져오기
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        // 프로필 이미지 경로 업데이트
        member.setProfileImage(imagePath);
        memberRepository.save(member);
    }

    public void existMember(String userId) {
        if (memberRepository.findById(userId).isEmpty()) {
            throw new RestApiException(MEMBER_NOT_FOUND);
        }
    }
}


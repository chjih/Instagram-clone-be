package com.example.InstagramCloneCoding.domain.member.application;

import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import com.example.InstagramCloneCoding.domain.member.dto.MemberResponseDto;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailConfirmService emailConfirmService;

    public MemberResponseDto register(MemberRegisterDto registerDto) {
        // 아이디 중복 확인
        memberRepository.findById(registerDto.getUserId())
                .ifPresent(member -> {
                    throw new RestApiException(ID_ALREADY_EXISTS);
                });

        // 이메일 중복 확인
        memberRepository.findByEmail(registerDto.getEmail())
                .ifPresent(member -> {
                    throw new RestApiException(EMAIL_ALREADY_REGISTERED);
                });

//        // 비밀번호 확인
//        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
//            throw new RestApiException(WRONG_CONFIRM_PASSWORD);
//        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        // 저장
        Member member = Member.builder()
                .email(registerDto.getEmail())
                .userId(registerDto.getUserId())
                .name(registerDto.getName())
                .password(encodedPassword)
                .lastHomeAccessTime(LocalDateTime.now())
                .build();
        memberRepository.save(member);

        // 이메일 인증 메일 보내기
        emailConfirmService.createEmailConfirmationToken(member.getUserId(), member.getEmail());

        return member.MemberToResponseDto();
    }

    public void changeProfileImage(String userId, String imagePath) {
        // 멤버 가져오기
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        // 프로필 이미지 경로 업데이트
        member.setProfileImage(imagePath);
        memberRepository.save(member);
    }
}


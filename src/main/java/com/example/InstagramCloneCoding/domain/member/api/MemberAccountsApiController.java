package com.example.InstagramCloneCoding.domain.member.api;

import com.example.InstagramCloneCoding.domain.member.application.EmailConfirmService;
import com.example.InstagramCloneCoding.domain.member.application.MemberService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import com.example.InstagramCloneCoding.domain.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("accounts/")
@RequiredArgsConstructor
public class MemberAccountsApiController {

    private final MemberService memberService;

    private final EmailConfirmService emailConfirmService;

    @PostMapping("emailsignup")
    public ResponseEntity<MemberResponseDto> register(@RequestBody MemberRegisterDto registerDto) {
        // 회원가입 (member 테이블에 추가 & 인증메일 보내기)
        MemberResponseDto memberResponseDto = memberService.register(registerDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberResponseDto);
    }

    @GetMapping("confirm-email")
    public ResponseEntity<Member> confirmEmail(@RequestParam String token) {
        // 이메일 인증 완료하고 member 테이블의 email_verified 컬럼 true로 바꿔주기
        emailConfirmService.confirmEmail(token);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
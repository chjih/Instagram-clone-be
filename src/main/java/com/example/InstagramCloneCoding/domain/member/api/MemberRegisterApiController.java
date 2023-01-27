package com.example.InstagramCloneCoding.domain.member.api;

import com.example.InstagramCloneCoding.domain.member.application.EmailConfirmService;
import com.example.InstagramCloneCoding.domain.member.application.MemberRegisterService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;


@RestController
@Transactional
public class MemberRegisterApiController {

    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private EmailConfirmService emailConfirmService;

    @PostMapping("register")
    public ResponseEntity<Member> register(@RequestBody MemberRegisterDto registerDto) {
        // 회원가입 (member 테이블에 추가)
        Member member = memberRegisterService.register(registerDto);

        // 이메일 인증 메일 보내기
        emailConfirmService.createEmailConfirmationToken(member.getUserId(), member.getEmail());

        return ResponseEntity.status(HttpStatus.OK)
                .body(member);
    }

    @GetMapping("confirm-email")
    public ResponseEntity<Member> confirmEmail(@RequestParam String token) {
        // 이메일 인증 완료하고 member 테이블의 email_verified 컬럼 true로 바꿔주기
        return ResponseEntity.status(HttpStatus.OK)
                .body(emailConfirmService.confirmEmail(token));
    }
}
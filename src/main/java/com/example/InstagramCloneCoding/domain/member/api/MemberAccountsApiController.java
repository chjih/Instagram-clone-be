package com.example.InstagramCloneCoding.domain.member.api;

import com.example.InstagramCloneCoding.domain.member.application.MemberService;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import com.example.InstagramCloneCoding.domain.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts/")
@RequiredArgsConstructor
public class MemberAccountsApiController {

    private final MemberService memberService;

    @PostMapping("check")
    public ResponseEntity<String> check(@RequestBody MemberRegisterDto registerDto) {
        // 중복 가입인지 확인하고 인증 메일 보내기
        String code = memberService.checkAndSendMail(registerDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(code);
    }

    @PostMapping("save")
    public ResponseEntity<MemberResponseDto> save(@RequestBody MemberRegisterDto registerDto) {
        // member 테이블에 저장
        MemberResponseDto memberResponseDto = memberService.saveMember(registerDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberResponseDto);
    }
}
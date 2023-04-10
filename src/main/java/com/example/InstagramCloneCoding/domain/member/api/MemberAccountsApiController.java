package com.example.InstagramCloneCoding.domain.member.api;

import com.example.InstagramCloneCoding.domain.member.application.MemberService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import com.example.InstagramCloneCoding.domain.member.dto.MemberResponseDto;
import com.example.InstagramCloneCoding.domain.member.dto.ProfileResponseDto;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("{member_id}")
    public ResponseEntity<ProfileResponseDto> profile(@Parameter(hidden = true) @LoggedInUser Member member,
                                                      @PathVariable(name = "member_id") String targetId) {
        ProfileResponseDto profileResponseDto = memberService.findAccount(member, targetId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(profileResponseDto);
    }

    @GetMapping("search/{member_id}")
    public ResponseEntity<List<MemberResponseDto>> search(@Parameter(hidden = true) @LoggedInUser Member member,
                                                          @PathVariable(name = "member_id") String targetId) {
        List<MemberResponseDto> members = memberService.search(targetId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(members);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> delete(@Parameter(hidden = true) @LoggedInUser Member member,
                                         @RequestBody Map<String, String> passwordMap) {
        memberService.deleteAccount(member, passwordMap.get("password"));

        return ResponseEntity.status(HttpStatus.OK)
                .body("delete account success!");
    }
}
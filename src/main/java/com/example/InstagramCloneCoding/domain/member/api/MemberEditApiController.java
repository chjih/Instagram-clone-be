package com.example.InstagramCloneCoding.domain.member.api;

import com.example.InstagramCloneCoding.domain.member.application.EmailConfirmService;
import com.example.InstagramCloneCoding.domain.member.application.MemberService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberEditDto;
import com.example.InstagramCloneCoding.domain.member.dto.ProfileResponseDto;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("edit/")
@RequiredArgsConstructor
public class MemberEditApiController {

    private final MemberService memberService;

    private final EmailConfirmService emailConfirmService;

    @PostMapping(value = "profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> changeProfileImage(@Parameter(hidden = true) @LoggedInUser Member member,
                                                     @RequestPart("image") List<MultipartFile> multipartFile) {

        String imagePath = memberService.changeProfileImage(member, multipartFile);

        return ResponseEntity.status(HttpStatus.OK)
                .body(imagePath);
    }

    @PostMapping("profile")
    public ResponseEntity<ProfileResponseDto> changeProfile(@Parameter(hidden = true) @LoggedInUser Member member,
                                                            @RequestBody MemberEditDto memberEditDto) {

        ProfileResponseDto profileResponseDto = memberService.changeProfile(member, memberEditDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(profileResponseDto);
    }

    @GetMapping("confirm-email")
    public ResponseEntity<Member> confirmEmail(@RequestParam String token) {

        // 변경된 이메일 인증 완료하고 member 테이블의 email_verified 컬럼 true로 바꿔주기
        emailConfirmService.confirmEmail(token);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}

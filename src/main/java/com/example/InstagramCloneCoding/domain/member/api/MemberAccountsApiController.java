package com.example.InstagramCloneCoding.domain.member.api;

import com.example.InstagramCloneCoding.domain.member.application.AwsS3Service;
import com.example.InstagramCloneCoding.domain.member.application.EmailConfirmService;
import com.example.InstagramCloneCoding.domain.member.application.MemberService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;


@RestController
@RequestMapping("accounts/")
@Transactional
public class MemberAccountsApiController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private EmailConfirmService emailConfirmService;
    @Autowired
    private AwsS3Service awsS3Service;

    @PostMapping("emailsignup")
    public ResponseEntity<Member> register(@RequestBody MemberRegisterDto registerDto) {
        // 회원가입 (member 테이블에 추가)
        Member member = memberService.register(registerDto);

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

    @PostMapping("edit/profile-image")
    public ResponseEntity<Member> changeProfileImage(@RequestParam("image")MultipartFile multipartFile,
                                                     @RequestParam("user_id") String userId) {
        String imagePath = awsS3Service.upload(multipartFile);

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.changeProfileImage(userId, imagePath));
    }
}
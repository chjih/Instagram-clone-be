package com.example.InstagramCloneCoding.domain.member.api;


import com.example.InstagramCloneCoding.domain.member.application.AwsS3Service;
import com.example.InstagramCloneCoding.domain.member.application.MemberService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RestController
@RequestMapping("edit/")
@Transactional
@RequiredArgsConstructor
public class MemberEditApiController {

    private final AwsS3Service awsS3Service;

    private final MemberService memberService;

    @PostMapping("profile-image")
    public ResponseEntity<String> changeProfileImage(@RequestParam("image") MultipartFile multipartFile,
                                                     @LoggedInUser Member member) {
        String imagePath = awsS3Service.upload(multipartFile);

        memberService.changeProfileImage(member.getUserId(), imagePath);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imagePath);
    }
}

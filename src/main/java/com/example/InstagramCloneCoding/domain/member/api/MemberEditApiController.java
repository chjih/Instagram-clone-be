package com.example.InstagramCloneCoding.domain.member.api;


import com.example.InstagramCloneCoding.domain.member.application.AwsS3Service;
import com.example.InstagramCloneCoding.domain.member.application.MemberService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("edit/")
@Transactional
@RequiredArgsConstructor
public class MemberEditApiController {

    private final AwsS3Service awsS3Service;

    private final MemberService memberService;

    @PostMapping(value = "profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> changeProfileImage(@Parameter(hidden = true) @LoggedInUser Member member,
                                                     @RequestPart("image") List<MultipartFile> multipartFile) {
        String imagePath = awsS3Service.uploadFile(multipartFile).get(0);

        memberService.changeProfileImage(member.getUserId(), imagePath);

        return ResponseEntity.status(HttpStatus.OK)
                .body(imagePath);
    }
}

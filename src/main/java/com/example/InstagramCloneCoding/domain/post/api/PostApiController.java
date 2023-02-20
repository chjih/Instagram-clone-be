package com.example.InstagramCloneCoding.domain.post.api;

import com.example.InstagramCloneCoding.domain.member.application.AwsS3Service;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.application.PostService;
import com.example.InstagramCloneCoding.domain.post.domain.Post;
import com.example.InstagramCloneCoding.domain.post.dto.PostResponseDto;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("post/")
@Transactional
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final AwsS3Service awsS3Service;

    @PostMapping(value = "write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDto> write(@Parameter(hidden = true) @LoggedInUser Member member,
                                @RequestPart("images") List<MultipartFile> images,
                                @RequestPart(value = "content", required = false) String content) {
        // s3 bucket에 이미지 업로드
        List<String> fileNameList = awsS3Service.uploadFile(images);

        // 데이터베이스에 저장
        PostResponseDto postResponseDto = postService.uploadPost(member, content, fileNameList);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseDto);
    }

    @GetMapping(value = "readAll")
    public ResponseEntity<List<PostResponseDto>> readAll(@Parameter(hidden = true) @LoggedInUser Member member) {
        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        member.getPosts().forEach(post -> {
            List<String> postImages = new ArrayList<>();
            post.getPostImages().forEach(postImage -> {
                postImages.add(postImage.getPostImageId());
            });

            PostResponseDto postResponseDto = new PostResponseDto(post.getPostId(), post.getContent(),
                    post.getCreatedAt(), postImages);

            postResponseDtos.add(postResponseDto);
        });

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseDtos);
    }
}

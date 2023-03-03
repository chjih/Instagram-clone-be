package com.example.InstagramCloneCoding.domain.post.api;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.application.PostFindService;
import com.example.InstagramCloneCoding.domain.post.application.PostService;
import com.example.InstagramCloneCoding.domain.post.dto.PostResponseDto;
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
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final PostFindService postFindService;

    @PostMapping(value = "/{member_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDto> write(@Parameter(hidden = true) @LoggedInUser Member member,
                                                 @RequestPart("images") List<MultipartFile> images,
                                                 @RequestPart(value = "content", required = false) String content) {

        PostResponseDto postResponseDto = postService.uploadPost(member, content, images);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseDto);
    }

    @GetMapping(value = "/{member_id}")
    public ResponseEntity<List<PostResponseDto>> readAll(@Parameter(hidden = true) @LoggedInUser Member member,
                                                         @PathVariable("member_id") String memberId) {
        List<PostResponseDto> postResponseDtos = postFindService.findAll(memberId, member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseDtos);
    }

    @GetMapping(value = "p/{post_id}")
    public ResponseEntity<PostResponseDto> read(@Parameter(hidden = true) @LoggedInUser Member member,
                                                @PathVariable("post_id") int postId) {
        PostResponseDto postResponseDto = postFindService.findByPostId(postId, member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseDto);
    }

    @DeleteMapping(value = "p/{post_id}")
    public ResponseEntity delete(@Parameter(hidden = true) @LoggedInUser Member member,
                                 @PathVariable("post_id") int postId) {
        postService.deletePost(member, postId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

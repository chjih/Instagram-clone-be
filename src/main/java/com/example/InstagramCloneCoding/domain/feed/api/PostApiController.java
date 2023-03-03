package com.example.InstagramCloneCoding.domain.feed.api;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.feed.application.PostFindService;
import com.example.InstagramCloneCoding.domain.feed.application.PostService;
import com.example.InstagramCloneCoding.domain.feed.dto.PostResponseDto;
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
@RequestMapping("post/")
public class PostApiController {

    private final PostService postService;
    private final PostFindService postFindService;

    @PostMapping(value = "write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDto> write(@Parameter(hidden = true) @LoggedInUser Member member,
                                                 @RequestPart("images") List<MultipartFile> images,
                                                 @RequestParam(value = "content", required = false) String content) {

        PostResponseDto postResponseDto = postService.uploadPost(member, content, images);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseDto);
    }

    @GetMapping(value = "readall/{member_id}")
    public ResponseEntity<List<PostResponseDto>> readAll(@Parameter(hidden = true) @LoggedInUser Member member,
                                                         @PathVariable("member_id") String memberId) {
        List<PostResponseDto> postResponseDtos = postFindService.findAll(memberId, member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseDtos);
    }

    @GetMapping(value = "read/{post_id}")
    public ResponseEntity<PostResponseDto> read(@Parameter(hidden = true) @LoggedInUser Member member,
                                                @PathVariable("post_id") int postId) {
        PostResponseDto postResponseDto = postFindService.findByPostId(postId, member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseDto);
    }

    @DeleteMapping(value = "delete/{post_id}")
    public ResponseEntity<String> delete(@Parameter(hidden = true) @LoggedInUser Member member,
                                 @PathVariable("post_id") int postId) {
        postService.deletePost(member, postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("delete success!");
    }

    @GetMapping("/home")
    public ResponseEntity<List<PostResponseDto>> getPosts(@Parameter(hidden = true) @LoggedInUser Member member) {
        List<PostResponseDto> posts = postFindService.getHomePosts(member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(posts);
    }
}

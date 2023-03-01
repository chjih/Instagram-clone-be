package com.example.InstagramCloneCoding.domain.postlike.api;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.postlike.application.PostLikeService;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeApiController {

    private final PostLikeService postLikeService;

    @PostMapping("/likepost")
    public ResponseEntity<String> likePost(@Parameter(hidden = true) @LoggedInUser Member member,
                                           @RequestParam("postid") int postId) {
        postLikeService.likePost(member, postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("post like success!");
    }

    @PostMapping("/unlikepost")
    public ResponseEntity<String> unlikePost(@Parameter(hidden = true) @LoggedInUser Member member,
                                             @RequestParam("postid") int postId) {
        postLikeService.unlikePost(member, postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("post like cancel success!");

    }
}

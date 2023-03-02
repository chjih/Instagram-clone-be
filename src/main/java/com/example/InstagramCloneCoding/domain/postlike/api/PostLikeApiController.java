package com.example.InstagramCloneCoding.domain.postlike.api;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.postlike.application.PostLikeService;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeApiController {

    private final PostLikeService postLikeService;

    @PostMapping("/post/{post_id}/like")
    public ResponseEntity<String> togglePostLike(@Parameter(hidden = true) @LoggedInUser Member member,
                                                 @PathVariable("postid") int postId) {
        postLikeService.togglePostLike(member, postId);

        return ResponseEntity.status(HttpStatus.OK).body("post like toggle success!");
    }
}

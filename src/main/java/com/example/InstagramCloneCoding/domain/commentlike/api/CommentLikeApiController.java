package com.example.InstagramCloneCoding.domain.commentlike.api;

import com.example.InstagramCloneCoding.domain.commentlike.application.CommentLikeService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
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
public class CommentLikeApiController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comment/{comment_id}/like")
    public ResponseEntity<String> toggleCommentLike(@Parameter(hidden = true) @LoggedInUser Member member,
                                                    @PathVariable(name = "comment_id") int commentId) {
        commentLikeService.toggleCommentLike(member, commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("comment like toggle success!");
    }
}

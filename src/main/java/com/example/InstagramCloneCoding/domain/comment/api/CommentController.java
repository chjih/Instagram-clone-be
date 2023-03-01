package com.example.InstagramCloneCoding.domain.comment.api;

import com.example.InstagramCloneCoding.domain.comment.application.CommentService;
import com.example.InstagramCloneCoding.domain.comment.dto.CommentDto;
import com.example.InstagramCloneCoding.domain.comment.dto.CommentResponseDto;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{post_id}")
    public ResponseEntity<CommentResponseDto> write(@Parameter(hidden = true) @LoggedInUser Member member,
                                @PathVariable("post_id") int postId,
                                @RequestBody CommentDto commentDto) {
        CommentResponseDto commentResponseDto = commentService.writeComment(member, postId, commentDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(commentResponseDto);
    }
}

package com.example.InstagramCloneCoding.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentResponseDto {

    private int commentId;

    private int postId;

    private String userId;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdAt;

    int ref;

    @Builder
    public CommentResponseDto(int commentId, int postId, String userId, String content, LocalDateTime createdAt, int ref) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.ref = ref;
    }
}

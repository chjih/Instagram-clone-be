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

    private String authorId;

    private String authorProfileImage;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdAt;

    private int ref;

    private int likes;

    private boolean iLiked;

    @Builder
    public CommentResponseDto(int commentId, int postId, String authorId, String content, LocalDateTime createdAt,
                              int ref, String authorProfileImage, int likes, boolean iLiked) {
        this.commentId = commentId;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.ref = ref;
        this.authorProfileImage = authorProfileImage;
        this.likes = likes;
        this.iLiked = iLiked;
    }
}

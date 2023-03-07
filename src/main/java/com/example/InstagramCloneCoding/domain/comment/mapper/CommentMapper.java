package com.example.InstagramCloneCoding.domain.comment.mapper;

import com.example.InstagramCloneCoding.domain.comment.domain.Comment;
import com.example.InstagramCloneCoding.domain.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public CommentResponseDto toDto(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .authorId(comment.getMember().getUserId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .ref(comment.getRef())
                .authorProfileImage(comment.getMember().getProfileImage())
                .build();
    }
}

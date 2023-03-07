package com.example.InstagramCloneCoding.domain.comment.mapper;

import com.example.InstagramCloneCoding.domain.comment.domain.Comment;
import com.example.InstagramCloneCoding.domain.comment.dto.CommentResponseDto;
import com.example.InstagramCloneCoding.domain.commentlike.dao.CommentLikeRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final CommentLikeRepository commentLikeRepository;
    public CommentResponseDto toDto(Member member, Comment comment) {
        boolean iLiked = commentLikeRepository.findByMemberAndComment(member, comment).isPresent();

        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .authorId(comment.getMember().getUserId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .ref(comment.getRef())
                .authorProfileImage(comment.getMember().getProfileImage())
                .likes(comment.getLikes().size())
                .iLiked(iLiked)
                .build();
    }
}

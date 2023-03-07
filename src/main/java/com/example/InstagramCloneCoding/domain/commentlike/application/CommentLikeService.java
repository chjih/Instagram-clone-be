package com.example.InstagramCloneCoding.domain.commentlike.application;

import com.example.InstagramCloneCoding.domain.comment.dao.CommentRepository;
import com.example.InstagramCloneCoding.domain.comment.domain.Comment;
import com.example.InstagramCloneCoding.domain.commentlike.dao.CommentLikeRepository;
import com.example.InstagramCloneCoding.domain.commentlike.domain.CommentLike;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.InstagramCloneCoding.domain.comment.error.CommentErrorCode.COMMENT_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    public void toggleCommentLike(Member member, int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(COMMENT_NOT_FOUND));
        CommentLike commentLike = commentLikeRepository.findByMemberAndComment(member, comment).orElse(null);

        if (commentLike == null) {
            commentLikeRepository.save(new CommentLike(member, comment));
        } else {
            commentLikeRepository.delete(commentLike);
        }
    }
}

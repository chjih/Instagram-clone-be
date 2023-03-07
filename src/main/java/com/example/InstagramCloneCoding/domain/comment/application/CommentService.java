package com.example.InstagramCloneCoding.domain.comment.application;

import com.example.InstagramCloneCoding.domain.comment.dao.CommentRepository;
import com.example.InstagramCloneCoding.domain.comment.domain.Comment;
import com.example.InstagramCloneCoding.domain.comment.dto.CommentDto;
import com.example.InstagramCloneCoding.domain.comment.dto.CommentResponseDto;
import com.example.InstagramCloneCoding.domain.comment.mapper.CommentMapper;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.feed.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.feed.domain.Post;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.InstagramCloneCoding.domain.comment.error.CommentErrorCode.COMMENT_NOT_FOUND;
import static com.example.InstagramCloneCoding.domain.comment.error.CommentErrorCode.UNAVAILABLE_COMMENT_REQUEST;
import static com.example.InstagramCloneCoding.domain.feed.error.PostErrorCode.POST_NOT_FOUND;
import static com.example.InstagramCloneCoding.global.error.CommonErrorCode.FORBIDDEN;

@Service
@Transactional
@RequiredArgsConstructor
public class
CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private int ref;

    private int refStep;

    public CommentResponseDto writeComment(Member member, int postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(POST_NOT_FOUND));

        // 대댓글인 경우 (CommentDto의 commentId가 null이 아닌 경우)
        if (commentDto.getCommentId() != null) {
            setReplyRef(post, commentDto);
        }
        // 대댓글이 아닌 경우 (CommentDto의 commentId가 null인 경우)
        else {
            setCommentRef(post, commentDto);
        }

        Comment comment = Comment.builder()
                .content(commentDto.getComment())
                .member(member)
                .post(post)
                .ref(ref)
                .refStep(refStep)
                .build();
        commentRepository.save(comment);

        return commentMapper.toDto(member, comment);
    }

    private void setReplyRef(Post post, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getCommentId())
                .orElseThrow(() -> new RestApiException(COMMENT_NOT_FOUND));
        if (comment.getPost().getPostId() != post.getPostId())
            throw new RestApiException(UNAVAILABLE_COMMENT_REQUEST);

        ref = comment.getRef();
        refStep = commentRepository.findByPostAndRef(post, comment.getRef()).size();
    }

    private void setCommentRef(Post post, CommentDto commentDto) {
        List<Comment> comments = post.getComments();

        // 첫 댓글인 경우
        if (comments.size() == 0) {
            ref = 0;
            refStep = 0;
        }
        else {
            ref = comments.get(comments.size() - 1).getRef() + 1;
            refStep = 0;
        }
    }

    public void deleteComment(Member member, int commentId) {
        Comment deleteComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(COMMENT_NOT_FOUND));

        // 댓글 작성자인지 확인
        if (!deleteComment.getMember().equals(member))
            throw new RestApiException(FORBIDDEN);

        // 댓글인 경우 해당 댓글에 속하는 대댓글까지 삭제
        if (deleteComment.getRefStep() == 0) {
            List<Comment> comments = commentRepository.findByRef(deleteComment.getRef());
            comments.forEach(commentRepository::delete);
        }
        // 대댓글인 경우 해당 대댓글만 삭제
        else {
            commentRepository.delete(deleteComment);
        }
    }

    public List<CommentResponseDto> readAllComments(Member member, int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findByPostOrderByRefAscRefStepAsc(post);

        return comments.stream()
                .map(comment -> commentMapper.toDto(member, comment))
                .collect(Collectors.toList());
    }
}

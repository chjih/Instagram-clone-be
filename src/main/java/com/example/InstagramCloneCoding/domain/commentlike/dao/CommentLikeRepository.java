package com.example.InstagramCloneCoding.domain.commentlike.dao;

import com.example.InstagramCloneCoding.domain.comment.domain.Comment;
import com.example.InstagramCloneCoding.domain.commentlike.domain.CommentLike;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {

    Optional<CommentLike> findByMemberAndComment(Member member, Comment comment);
}

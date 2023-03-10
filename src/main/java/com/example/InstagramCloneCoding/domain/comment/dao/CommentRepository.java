package com.example.InstagramCloneCoding.domain.comment.dao;

import com.example.InstagramCloneCoding.domain.comment.domain.Comment;
import com.example.InstagramCloneCoding.domain.feed.domain.Post;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPostAndRef(Post post, int ref);

    List<Comment> findByRef(int ref);

    List<Comment> findByPostOrderByRefAscRefStepAsc(Post post);

    List<Comment> findByMember(Member member);
}
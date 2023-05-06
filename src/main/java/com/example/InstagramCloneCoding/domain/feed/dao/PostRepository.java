package com.example.InstagramCloneCoding.domain.feed.dao;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.feed.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByAuthorInAndCreatedAtGreaterThanEqualOrderByCreatedAtDesc(List<Member> authors, LocalDateTime accessTime);

    List<Post> findByAuthor(Member author);
}

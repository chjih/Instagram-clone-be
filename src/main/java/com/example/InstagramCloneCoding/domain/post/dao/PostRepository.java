package com.example.InstagramCloneCoding.domain.post.dao;

import com.example.InstagramCloneCoding.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}

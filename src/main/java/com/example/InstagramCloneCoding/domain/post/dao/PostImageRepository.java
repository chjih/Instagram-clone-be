package com.example.InstagramCloneCoding.domain.post.dao;

import com.example.InstagramCloneCoding.domain.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, String> {
}

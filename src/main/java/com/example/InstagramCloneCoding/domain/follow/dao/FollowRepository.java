package com.example.InstagramCloneCoding.domain.follow.dao;

import com.example.InstagramCloneCoding.domain.follow.domain.Follow;
import com.example.InstagramCloneCoding.domain.follow.domain.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    List<Follow> findByFollowerId(String followId);

    List<Follow> findByFollowingId(String followingId);
}

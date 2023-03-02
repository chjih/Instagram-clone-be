package com.example.InstagramCloneCoding.domain.follow.dao;

import com.example.InstagramCloneCoding.domain.member.domain.Member;

import java.util.List;

public interface FollowRepositoryCustom {

    List<Member> findFollowersById(String followingId);
    List<Member> findFollowingsById(String followerId);
}

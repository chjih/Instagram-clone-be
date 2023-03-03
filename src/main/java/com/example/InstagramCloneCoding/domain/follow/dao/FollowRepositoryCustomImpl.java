package com.example.InstagramCloneCoding.domain.follow.dao;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.InstagramCloneCoding.domain.follow.domain.QFollow.follow;

@RequiredArgsConstructor
public class FollowRepositoryCustomImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Member> findFollowersById(String followingId) {
        return jpaQueryFactory.select(follow.follower)
                .from(follow)
                .where(follow.following.userId.eq(followingId))
                .fetch();
    }

    @Override
    public List<Member> findFollowingsById(String followerId) {
        return jpaQueryFactory.select(follow.following)
                .from(follow)
                .where(follow.follower.userId.eq(followerId))
                .fetch();
    }
}

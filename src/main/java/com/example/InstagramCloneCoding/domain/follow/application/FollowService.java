package com.example.InstagramCloneCoding.domain.follow.application;

import com.example.InstagramCloneCoding.domain.follow.dao.FollowRepository;
import com.example.InstagramCloneCoding.domain.follow.domain.Follow;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.InstagramCloneCoding.domain.follow.error.FollowError.ALREADY_FOLLOWING;
import static com.example.InstagramCloneCoding.domain.follow.error.FollowError.NOT_FOLLOWING;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public void follow(Member follower, Member following) {
        // follow 중복 검사
        followRepository.findByFollowerAndFollowing(follower, following)
                .ifPresent(x -> {
                    throw new RestApiException(ALREADY_FOLLOWING);
                });

        followRepository.save(new Follow(follower, following));
    }

    @Transactional
    public void unfollow(Member follower, Member following) {
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following).orElse(null);
        // follow 확인
        if (follow == null) {
            throw new RestApiException(NOT_FOLLOWING);
        }

        followRepository.delete(follow);
    }

    public List<String> getFollowers(Member member) {
        List<Follow> followerList = member.getFollowers();

        return followerList.stream()
                .map(x -> x.getFollower().getUserId())
                .collect(Collectors.toList());
    }

    public List<String> getFollowings(Member member) {
        List<Follow> followingList = member.getFollowings();

        return followingList.stream()
                .map(x -> x.getFollowing().getUserId())
                .collect(Collectors.toList());
    }

    public List<String> getFollowBacks(Member member) {
        List<String> followBackList = getFollowings(member);
        followBackList.retainAll(getFollowers(member));
        return followBackList;
    }
}

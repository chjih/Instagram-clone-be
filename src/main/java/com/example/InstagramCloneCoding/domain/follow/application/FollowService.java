package com.example.InstagramCloneCoding.domain.follow.application;

import com.example.InstagramCloneCoding.domain.follow.dao.FollowRepository;
import com.example.InstagramCloneCoding.domain.follow.domain.Follow;
import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.InstagramCloneCoding.domain.follow.error.FollowError.ALREADY_FOLLOWING;
import static com.example.InstagramCloneCoding.domain.follow.error.FollowError.NOT_FOLLOWING;
import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void follow(Member follower, String followingId) {
        Member following = memberRepository.findById(followingId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        // follow 중복 검사
        followRepository.findByFollowerAndFollowing(follower, following)
                .ifPresent(x -> {
                    throw new RestApiException(ALREADY_FOLLOWING);
                });

        followRepository.save(new Follow(follower, following));
    }

    @Transactional
    public void unfollow(Member follower, String followingId) {
        Member following = memberRepository.findById(followingId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following).orElse(null);
        // follow 확인
        if (follow == null) {
            throw new RestApiException(NOT_FOLLOWING);
        }

        followRepository.delete(follow);
    }
}

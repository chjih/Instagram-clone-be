package com.example.InstagramCloneCoding.domain.member.application;

import com.example.InstagramCloneCoding.domain.follow.domain.Follow;
import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberFindService {
    private final MemberRepository memberRepository;

    public Member findMember(String userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));
    }

    public List<Member> findFollowers(Member member) {
        List<Follow> followerList = member.getFollowers();

        return followerList.stream()
                .map(Follow::getFollower)
                .collect(Collectors.toList());
    }

    public List<Member> findFollowings(Member member) {
        List<Follow> followingList = member.getFollowings();

        return followingList.stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());
    }
}
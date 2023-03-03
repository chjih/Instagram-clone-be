package com.example.InstagramCloneCoding.domain.follow.application;

import com.example.InstagramCloneCoding.domain.follow.dao.FollowRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowFindService {

    private final FollowRepository followRepository;

    public List<MemberResponseDto> getFollowers(String memberId) {
        List<Member> followers = followRepository.findFollowersById(memberId);

        return followers.stream()
                .map(Member::memberToResponseDto)
                .collect(Collectors.toList());
    }

    public List<MemberResponseDto> getFollowings(String memberId) {
        List<Member> followings = followRepository.findFollowingsById(memberId);

        return followings.stream()
                .map(Member::memberToResponseDto)
                .collect(Collectors.toList());
    }
}
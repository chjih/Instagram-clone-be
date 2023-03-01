package com.example.InstagramCloneCoding.domain.follow.application;

import com.example.InstagramCloneCoding.domain.member.application.MemberFindService;
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

    private final MemberFindService memberFindService;

    public List<MemberResponseDto> getFollowers(String memberId) {
        Member member = memberFindService.findMember(memberId);
        List<Member> followers = memberFindService.findFollowers(member);

        return followers.stream()
                .map(Member::memberToResponseDto)
                .collect(Collectors.toList());
    }

    public List<MemberResponseDto> getFollowings(String memberId) {
        Member member = memberFindService.findMember(memberId);
        List<Member> followings = memberFindService.findFollowings(member);

        return followings.stream()
                .map(Member::memberToResponseDto)
                .collect(Collectors.toList());
    }
}
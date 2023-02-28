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

    public List<MemberResponseDto> getFollowersId(Member member) {
        List<Member> followers = memberFindService.findFollowers(member);

        return getIds(followers);
    }

    public List<MemberResponseDto> getFollowingsId(Member member) {
        List<Member> followings = memberFindService.findFollowings(member);

        return getIds(followings);
    }

    public List<MemberResponseDto> getFollowingBacksId(Member member) {
        List<Member> friends = memberFindService.findFollowBacks(member);

        return getIds(friends);
    }

    private List<MemberResponseDto> getIds(List<Member> members) {
        return members.stream()
                .map(Member::memberToResponseDto)
                .collect(Collectors.toList());
    }
}

package com.example.InstagramCloneCoding.domain.follow.application;

import com.example.InstagramCloneCoding.domain.member.application.MemberFindService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
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

    public List<String> getFollowersId(Member member) {
        List<Member> followers = memberFindService.findFollowers(member);

        return getIds(followers);
    }

    public List<String> getFollowingsId(Member member) {
        List<Member> followings = memberFindService.findFollowings(member);

        return getIds(followings);
    }

    public List<String> getFollowingBacksId(Member member) {
        List<Member> friends = memberFindService.findFollowBacks(member);

        return getIds(friends);
    }

    private List<String> getIds(List<Member> friends) {
        return friends.stream()
                .map(Member::getUserId)
                .collect(Collectors.toList());
    }
}

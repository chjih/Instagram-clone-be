package com.example.InstagramCloneCoding.domain.member.mapper;

import com.example.InstagramCloneCoding.domain.follow.dao.FollowRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberResponseDto;
import com.example.InstagramCloneCoding.domain.member.dto.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final FollowRepository followRepository;

    public MemberResponseDto toMemberResponseDto(Member entity) {
        return new MemberResponseDto(entity.getUserId(), entity.getName(), entity.getProfileImage());
    }

    public ProfileResponseDto toProfileResponse(Member entity, Member member) {
        boolean isMe = entity.equals(member);
        boolean iFollowed = followRepository.findByFollowerAndFollowing(member, entity).isPresent();

        return ProfileResponseDto.builder()
                .userId(entity.getUserId())
                .name(entity.getName())
                .profileImage(entity.getProfileImage())
                .introduction(entity.getIntroduction())
                .followerCount(entity.getFollowers().size())
                .followingCount(entity.getFollowings().size())
                .iFollowed(iFollowed)
                .isMe(isMe)
                .build();
    }
}

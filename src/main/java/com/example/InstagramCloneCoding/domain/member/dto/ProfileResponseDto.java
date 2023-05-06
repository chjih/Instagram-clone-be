package com.example.InstagramCloneCoding.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {
    private String userId;

    private String name;

    private String profileImage;

    private String introduction;

    private int postCount;

    private int followerCount;

    private int followingCount;

    private boolean iFollowed;

    private boolean isMe;

    @Builder
    public ProfileResponseDto(String userId, String name, String profileImage, String introduction,
                              int postCount, int followerCount, int followingCount, boolean iFollowed, boolean isMe) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.iFollowed = iFollowed;
        this.isMe = isMe;
    }
}

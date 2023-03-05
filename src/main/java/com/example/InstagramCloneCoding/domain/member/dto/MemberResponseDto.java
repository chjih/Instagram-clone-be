package com.example.InstagramCloneCoding.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponseDto {

    private String userId;

    private String name;

    private String profileImage;
}

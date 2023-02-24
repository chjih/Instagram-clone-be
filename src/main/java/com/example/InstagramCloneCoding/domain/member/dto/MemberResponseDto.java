package com.example.InstagramCloneCoding.domain.member.dto;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MemberResponseDto {

    private String userId;

    private String email;

    private String name;

    private String profileImage;

    private String introduction;
}

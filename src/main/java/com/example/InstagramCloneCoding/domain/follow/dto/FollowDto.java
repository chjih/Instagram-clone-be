package com.example.InstagramCloneCoding.domain.follow.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FollowDto {

    @NotBlank
    private String followingOrFollowerId;
}
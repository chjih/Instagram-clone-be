package com.example.InstagramCloneCoding.domain.follow.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FollowId implements Serializable {

    @Column(name = "follower_id", nullable = false)
    String followerId;

    @Column(name = "following_id", nullable = false)
    String followingId;
}

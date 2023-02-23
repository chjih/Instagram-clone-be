package com.example.InstagramCloneCoding.domain.member.domain;

import com.example.InstagramCloneCoding.domain.follow.domain.Follow;
import com.example.InstagramCloneCoding.domain.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "user_id")
    String userId;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "profile_image")
    String profileImage;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "introduction")
    String introduction;

    @Column(name = "email_verified", nullable = false)
    boolean emailVerified;

    @Column(name = "last_home_access_time")
    LocalDateTime lastHomeAccessed;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> followers = new ArrayList<>();

    @Builder
    public Member(String email, String userId, String name, String password, LocalDateTime lastHomeAccessed) {
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.emailVerified = false;
        this.lastHomeAccessed = lastHomeAccessed;
    }
}

package com.example.InstagramCloneCoding.domain.member.domain;

import com.example.InstagramCloneCoding.domain.follow.domain.Follow;
import com.example.InstagramCloneCoding.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> followers = new ArrayList<>();

    public Member(String email, String userId, String name, String password) {
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.emailVerified = false;
    }
}

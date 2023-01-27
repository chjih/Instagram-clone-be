package com.example.InstagramCloneCoding.domain.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member")
@Getter @Setter
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

    public Member(String email, String userId, String password) {
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.emailVerified = false;
    }
}

package com.example.InstagramCloneCoding.domain.postlike.domain;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.feed.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "post_like")
@NoArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id", nullable = false)
    private int postLikeId;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne
    private Post post;

    public PostLike(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}

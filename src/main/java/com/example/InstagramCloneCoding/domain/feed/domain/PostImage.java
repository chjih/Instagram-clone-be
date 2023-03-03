package com.example.InstagramCloneCoding.domain.feed.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "post_image")
@Getter @Setter
@NoArgsConstructor
public class PostImage {

    @Id
    @Column(name = "post_image_id", nullable = false)
    private String postImageId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public PostImage(String postImageId, Post post) {
        this.postImageId = postImageId;
        this.post = post;
    }
}

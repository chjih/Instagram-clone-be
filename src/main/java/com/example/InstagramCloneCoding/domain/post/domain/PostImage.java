package com.example.InstagramCloneCoding.domain.post.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "post_image")
@Getter
@Setter
@NoArgsConstructor
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id", nullable = false)
    private int postImageId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}

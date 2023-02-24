package com.example.InstagramCloneCoding.domain.post.domain;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.dto.PostResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "content", nullable = true)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();

    public Post(Member member, String content) {
        this.member = member;
        this.content = content;
    }

    public PostResponseDto postToResponseDto() {
       return PostResponseDto.builder()
               .postId(postId)
               .authorId(member.getUserId())
               .content(content)
               .createdAt(createdAt)
               .postImages(postImages)
               .build();
    }
}
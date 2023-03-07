package com.example.InstagramCloneCoding.domain.comment.domain;

import com.example.InstagramCloneCoding.domain.commentlike.domain.CommentLike;
import com.example.InstagramCloneCoding.domain.feed.domain.Post;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import lombok.Builder;
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
@Table(name = "comment")
@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private int commentId;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "ref")
    private int ref;

    @Column(name = "ref_step")
    private int refStep;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>();

    @Builder
    public Comment(String content, Member member, Post post, int ref, int refStep) {
        this.content = content;
        this.member = member;
        this.post = post;
        this.ref = ref;
        this.refStep = refStep;
    }
}

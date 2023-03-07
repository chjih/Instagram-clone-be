package com.example.InstagramCloneCoding.domain.commentlike.domain;

import com.example.InstagramCloneCoding.domain.comment.domain.Comment;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "comment_like")
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    int commentId;

    @JoinColumn(name = "user_id")
    @ManyToOne
    Member member;

    @JoinColumn(name = "comment_id")
    @ManyToOne
    Comment comment;

    public CommentLike(Member member, Comment comment) {
        this.member = member;
        this.comment = comment;
    }
}

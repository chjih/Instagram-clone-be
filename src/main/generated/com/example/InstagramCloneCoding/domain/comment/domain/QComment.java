package com.example.InstagramCloneCoding.domain.comment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -1914517882L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final NumberPath<Integer> commentId = createNumber("commentId", Integer.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ListPath<com.example.InstagramCloneCoding.domain.commentlike.domain.CommentLike, com.example.InstagramCloneCoding.domain.commentlike.domain.QCommentLike> likes = this.<com.example.InstagramCloneCoding.domain.commentlike.domain.CommentLike, com.example.InstagramCloneCoding.domain.commentlike.domain.QCommentLike>createList("likes", com.example.InstagramCloneCoding.domain.commentlike.domain.CommentLike.class, com.example.InstagramCloneCoding.domain.commentlike.domain.QCommentLike.class, PathInits.DIRECT2);

    public final com.example.InstagramCloneCoding.domain.member.domain.QMember member;

    public final com.example.InstagramCloneCoding.domain.feed.domain.QPost post;

    public final NumberPath<Integer> ref = createNumber("ref", Integer.class);

    public final NumberPath<Integer> refStep = createNumber("refStep", Integer.class);

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.InstagramCloneCoding.domain.member.domain.QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new com.example.InstagramCloneCoding.domain.feed.domain.QPost(forProperty("post"), inits.get("post")) : null;
    }

}


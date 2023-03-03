package com.example.InstagramCloneCoding.domain.feed.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -756402980L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.example.InstagramCloneCoding.domain.member.domain.QMember author;

    public final ListPath<com.example.InstagramCloneCoding.domain.comment.domain.Comment, com.example.InstagramCloneCoding.domain.comment.domain.QComment> comments = this.<com.example.InstagramCloneCoding.domain.comment.domain.Comment, com.example.InstagramCloneCoding.domain.comment.domain.QComment>createList("comments", com.example.InstagramCloneCoding.domain.comment.domain.Comment.class, com.example.InstagramCloneCoding.domain.comment.domain.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ListPath<com.example.InstagramCloneCoding.domain.postlike.domain.PostLike, com.example.InstagramCloneCoding.domain.postlike.domain.QPostLike> likes = this.<com.example.InstagramCloneCoding.domain.postlike.domain.PostLike, com.example.InstagramCloneCoding.domain.postlike.domain.QPostLike>createList("likes", com.example.InstagramCloneCoding.domain.postlike.domain.PostLike.class, com.example.InstagramCloneCoding.domain.postlike.domain.QPostLike.class, PathInits.DIRECT2);

    public final NumberPath<Integer> postId = createNumber("postId", Integer.class);

    public final ListPath<PostImage, QPostImage> postImages = this.<PostImage, QPostImage>createList("postImages", PostImage.class, QPostImage.class, PathInits.DIRECT2);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.example.InstagramCloneCoding.domain.member.domain.QMember(forProperty("author")) : null;
    }

}


package com.example.InstagramCloneCoding.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1280701614L;

    public static final QMember member = new QMember("member1");

    public final StringPath email = createString("email");

    public final BooleanPath emailVerified = createBoolean("emailVerified");

    public final ListPath<com.example.InstagramCloneCoding.domain.follow.domain.Follow, com.example.InstagramCloneCoding.domain.follow.domain.QFollow> followers = this.<com.example.InstagramCloneCoding.domain.follow.domain.Follow, com.example.InstagramCloneCoding.domain.follow.domain.QFollow>createList("followers", com.example.InstagramCloneCoding.domain.follow.domain.Follow.class, com.example.InstagramCloneCoding.domain.follow.domain.QFollow.class, PathInits.DIRECT2);

    public final ListPath<com.example.InstagramCloneCoding.domain.follow.domain.Follow, com.example.InstagramCloneCoding.domain.follow.domain.QFollow> followings = this.<com.example.InstagramCloneCoding.domain.follow.domain.Follow, com.example.InstagramCloneCoding.domain.follow.domain.QFollow>createList("followings", com.example.InstagramCloneCoding.domain.follow.domain.Follow.class, com.example.InstagramCloneCoding.domain.follow.domain.QFollow.class, PathInits.DIRECT2);

    public final StringPath introduction = createString("introduction");

    public final DateTimePath<java.time.LocalDateTime> lastHomeAccessTime = createDateTime("lastHomeAccessTime", java.time.LocalDateTime.class);

    public final ListPath<com.example.InstagramCloneCoding.domain.postlike.domain.PostLike, com.example.InstagramCloneCoding.domain.postlike.domain.QPostLike> likePosts = this.<com.example.InstagramCloneCoding.domain.postlike.domain.PostLike, com.example.InstagramCloneCoding.domain.postlike.domain.QPostLike>createList("likePosts", com.example.InstagramCloneCoding.domain.postlike.domain.PostLike.class, com.example.InstagramCloneCoding.domain.postlike.domain.QPostLike.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final ListPath<com.example.InstagramCloneCoding.domain.feed.domain.Post, com.example.InstagramCloneCoding.domain.feed.domain.QPost> posts = this.<com.example.InstagramCloneCoding.domain.feed.domain.Post, com.example.InstagramCloneCoding.domain.feed.domain.QPost>createList("posts", com.example.InstagramCloneCoding.domain.feed.domain.Post.class, com.example.InstagramCloneCoding.domain.feed.domain.QPost.class, PathInits.DIRECT2);

    public final StringPath profileImage = createString("profileImage");

    public final StringPath userId = createString("userId");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}


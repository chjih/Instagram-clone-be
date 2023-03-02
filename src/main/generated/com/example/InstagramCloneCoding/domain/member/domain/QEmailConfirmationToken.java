package com.example.InstagramCloneCoding.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailConfirmationToken is a Querydsl query type for EmailConfirmationToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailConfirmationToken extends EntityPathBase<EmailConfirmationToken> {

    private static final long serialVersionUID = -507263168L;

    public static final QEmailConfirmationToken emailConfirmationToken = new QEmailConfirmationToken("emailConfirmationToken");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expirationDate = createDateTime("expirationDate", java.time.LocalDateTime.class);

    public final BooleanPath expired = createBoolean("expired");

    public final StringPath id = createString("id");

    public final StringPath userId = createString("userId");

    public QEmailConfirmationToken(String variable) {
        super(EmailConfirmationToken.class, forVariable(variable));
    }

    public QEmailConfirmationToken(Path<? extends EmailConfirmationToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailConfirmationToken(PathMetadata metadata) {
        super(EmailConfirmationToken.class, metadata);
    }

}


package com.example.InstagramCloneCoding.domain.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_confirmation_token")
@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EmailConfirmationToken {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L; // 토큰 만료 시간

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "expired", nullable = false)
    private boolean expired;

    @Column(name = "user_id", nullable = false)
    private String userId; // FK 아님

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

/*    @LastModifiedDate
    @Column
    private LocalDateTime lastModifiedDate;*/

    public static EmailConfirmationToken createEmailConfirmationToken(String userId) {
        EmailConfirmationToken token = new EmailConfirmationToken();
        token.setExpirationDate(LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE));
        token.setUserId(userId);
        token.setExpired(false);

        return token;
    }

    public void useToken() {
        expired = true;
    }
}

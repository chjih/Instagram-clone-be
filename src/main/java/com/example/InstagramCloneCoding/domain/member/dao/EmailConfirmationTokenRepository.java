package com.example.InstagramCloneCoding.domain.member.dao;

import com.example.InstagramCloneCoding.domain.member.domain.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, String> {

    Optional<EmailConfirmationToken> findByIdAndExpirationDateAfterAndExpired(
            String emailConfirmationTokenId, LocalDateTime now, boolean expired);
}

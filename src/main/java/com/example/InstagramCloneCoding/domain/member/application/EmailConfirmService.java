package com.example.InstagramCloneCoding.domain.member.application;

import com.example.InstagramCloneCoding.domain.member.dao.EmailConfirmationTokenRepository;
import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import com.example.InstagramCloneCoding.domain.member.domain.EmailConfirmationToken;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;

import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.TOKEN_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailConfirmService {

    private final MemberRepository memberRepository;

    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;

    private final EmailSenderService emailSenderService;

    @Value("${server.url}")
    private String serverUrl;

    public void createEmailConfirmationToken(String userId, String receiverEmail) {
        EmailConfirmationToken token = EmailConfirmationToken.createEmailConfirmationToken(userId);
        emailConfirmationTokenRepository.save(token);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject("인스타그램(클론) 회원가입 이메일 인증");
        message.setText(serverUrl + "accounts/confirm-email?token=" + token.getId());
        emailSenderService.sendEmail(message);
    }

    public void confirmEmail(String token) {
        EmailConfirmationToken findToken = emailConfirmationTokenRepository
                .findByIdAndExpirationDateAfterAndExpired(token, LocalDateTime.now(), false)
                .orElseThrow(() -> new RestApiException(TOKEN_NOT_FOUND));
        Member member = memberRepository.findById(findToken.getUserId())
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        findToken.useToken();
        member.setEmailVerified(true);
        emailConfirmationTokenRepository.save(findToken);
        memberRepository.save(member);
    }

    public String createEmailAuthenticationCode(String receiverEmail) {
        // 랜덤 인증 코드 생성
        String code;
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++)
            codeBuilder.append(random.nextInt(9));
        code = codeBuilder.toString();

        // 메일 보내기
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject("인스타그램(클론) 회원가입 이메일 인증");
        message.setText("이메일 인증 코드는 " + code + " 입니다.");
        emailSenderService.sendEmail(message);

        return code;
    }
}

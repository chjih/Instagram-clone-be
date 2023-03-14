package com.example.InstagramCloneCoding.domain.member.application;

import com.example.InstagramCloneCoding.domain.comment.dao.CommentRepository;
import com.example.InstagramCloneCoding.domain.comment.domain.Comment;
import com.example.InstagramCloneCoding.domain.feed.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberEditDto;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import com.example.InstagramCloneCoding.domain.member.dto.MemberResponseDto;
import com.example.InstagramCloneCoding.domain.member.dto.ProfileResponseDto;
import com.example.InstagramCloneCoding.domain.member.mapper.MemberMapper;
import com.example.InstagramCloneCoding.global.common.aws.AwsS3Service;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailConfirmService emailConfirmService;

    private final AwsS3Service awsS3Service;

    private final MemberMapper memberMapper;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public MemberResponseDto saveMember(MemberRegisterDto memberRegisterDto) {
        // 아이디 중복 확인
        memberRepository.findById(memberRegisterDto.getUserId())
                .ifPresent(member -> {
                    throw new RestApiException(ID_ALREADY_EXISTS);
                });

        // 이메일 중복 확인
        memberRepository.findByEmail(memberRegisterDto.getEmail())
                .ifPresent(member -> {
                    throw new RestApiException(EMAIL_ALREADY_REGISTERED);
                });

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberRegisterDto.getPassword());

        // 저장
        Member member = Member.builder()
                .email(memberRegisterDto.getEmail())
                .userId(memberRegisterDto.getUserId())
                .name(memberRegisterDto.getName())
                .password(encodedPassword)
                .lastHomeAccessTime(LocalDateTime.now())
                .build();
        memberRepository.save(member);

        return memberMapper.toMemberResponseDto(member);
    }

    public String checkAndSendMail(MemberRegisterDto memberRegisterDto) {
        // 아이디 중복 확인
        memberRepository.findById(memberRegisterDto.getUserId())
                .ifPresent(member -> {
                    throw new RestApiException(ID_ALREADY_EXISTS);
                });

        // 이메일 중복 확인
        memberRepository.findByEmail(memberRegisterDto.getEmail())
                .ifPresent(member -> {
                    throw new RestApiException(EMAIL_ALREADY_REGISTERED);
                });

        return emailConfirmService.createEmailAuthenticationCode(memberRegisterDto.getEmail());
    }

    public String changeProfileImage(Member member, List<MultipartFile> multipartFile) {
        // 기존 프로필 사진 s3 버킷에서 삭제
        if (member.getProfileImage() != null)
            awsS3Service.deleteFile(member.getProfileImage());

        // s3 버킷에 이미지 저장
        String imagePath = awsS3Service.uploadFile(multipartFile).get(0);

        // 프로필 이미지 경로 업데이트
        member.setProfileImage(imagePath);

        return imagePath;
    }

    public ProfileResponseDto changeProfile(Member member, MemberEditDto memberEditDto) {
        // 이메일 변경되었는지 확인 -> 변경되었으면 인증용 메일 보내기
        if (!member.getEmail().equals(memberEditDto.getEmail())) {
            // 이메일 중복 확인
            memberRepository.findByEmail(memberEditDto.getEmail())
                    .ifPresent(m -> {
                        throw new RestApiException(EMAIL_ALREADY_REGISTERED);
                    });

            member.setEmailVerified(false);
            member.setEmail(memberEditDto.getEmail());
            emailConfirmService.createEmailConfirmationToken(member.getUserId(), member.getEmail());
        }

        // 이름과 소개글 변경
        member.setName(memberEditDto.getName());
        member.setIntroduction(memberEditDto.getIntroduction());

        return memberMapper.toProfileResponse(member, member);
    }

    public ProfileResponseDto findAccount(Member member, String targetId) {
        Member target = memberRepository.findById(targetId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        return memberMapper.toProfileResponse(target, member);
    }

    public void deleteAccount(Member member, String password) {
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, member.getPassword()))
            throw new RestApiException(WRONG_CONFIRM_PASSWORD);

        // 프로필 이미지 삭제
        if (member.getProfileImage() != null)
            awsS3Service.deleteFile(member.getProfileImage());

        // 작성한 포스트의 이미지 삭제
        postRepository.findByAuthor(member).forEach(post ->
            post.getPostImages().forEach(postImage ->
                    awsS3Service.deleteFile(postImage.getPostImageId())));

        // 작성한 댓글 삭제
        commentRepository.findByMember(member).forEach(comment -> {
            if (comment.getRefStep() == 0) {
                List<Comment> comments = commentRepository.findByRef(comment.getRef());
                comments.forEach(commentRepository::delete);
            }
            else {
                commentRepository.delete(comment);
            }
        });

        // Member DB에서 삭제
        memberRepository.delete(member);
    }
}


package com.example.InstagramCloneCoding.domain.post.application;

import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.post.domain.Post;
import com.example.InstagramCloneCoding.domain.post.dto.PostResponseDto;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.example.InstagramCloneCoding.domain.post.error.PostErrorCode.POST_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PostFindService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResponseDto findByPostId(int postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(POST_NOT_FOUND));

        return post.postToResponseDto(member);
    }

    public List<PostResponseDto> findAll(String memberId, Member member) {
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        return author.getPosts()
                .stream()
                .map(post -> post.postToResponseDto(member))
                .collect(Collectors.toList());
    }
}

package com.example.InstagramCloneCoding.domain.home.application;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.post.domain.Post;
import com.example.InstagramCloneCoding.domain.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeService {

    private final PostRepository postRepository;

    public List<PostResponseDto> getHomePosts(Member member, List<Member> followers){
        List<Post> posts = postRepository.findByMemberInAndCreatedAtGreaterThanEqual(followers, member.getLastHomeAccessTime());
        member.setLastHomeAccessTime(LocalDateTime.now());

        return posts.stream()
                .map(post->PostResponseDto.builder()
                        .postId(post.getPostId())
                        .authorId(post.getMember().getUserId())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .postImages(post.getPostImages())
                        .build())
                .collect(Collectors.toList());
    }
}

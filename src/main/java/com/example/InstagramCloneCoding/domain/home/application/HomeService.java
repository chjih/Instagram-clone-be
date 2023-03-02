package com.example.InstagramCloneCoding.domain.home.application;

import com.example.InstagramCloneCoding.domain.follow.dao.FollowRepository;
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
    private final FollowRepository followRepository;

    public List<PostResponseDto> getHomePosts(Member member) {
        List<Post> posts = postRepository.findByAuthorInAndCreatedAtGreaterThanEqual(
                followRepository.findFollowingsById(member.getUserId()),
                member.getLastHomeAccessTime());

        member.setLastHomeAccessTime(LocalDateTime.now());

        return posts.stream()
                .map(Post::postToResponseDto)
                .collect(Collectors.toList());
    }
}

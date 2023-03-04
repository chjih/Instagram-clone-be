package com.example.InstagramCloneCoding.domain.feed.mapper;

import com.example.InstagramCloneCoding.domain.feed.domain.Post;
import com.example.InstagramCloneCoding.domain.feed.dto.PostResponseDto;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.postlike.dao.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final PostLikeRepository postLikeRepository;

    public PostResponseDto toDto(Post post, Member member) {
        boolean iLiked=postLikeRepository.findByMemberAndPost(member,post).isPresent();

        return PostResponseDto.builder()
                .postId(post.getPostId())
                .authorId(post.getAuthor().getUserId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .postImages(post.getPostImages())
                .likes(post.getLikes().size())
                .iLiked(iLiked)
                .authorProfileImage(member.getProfileImage())
                .build();
    }
}

package com.example.InstagramCloneCoding.domain.feed.application;

import com.example.InstagramCloneCoding.domain.follow.dao.FollowRepository;
import com.example.InstagramCloneCoding.domain.member.dao.MemberRepository;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.feed.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.feed.domain.Post;
import com.example.InstagramCloneCoding.domain.feed.dto.PostResponseDto;
import com.example.InstagramCloneCoding.domain.postlike.dao.PostLikeRepository;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.example.InstagramCloneCoding.domain.feed.error.PostErrorCode.POST_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PostFindService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final FollowRepository followRepository;

    public PostResponseDto findByPostId(int postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(POST_NOT_FOUND));

        return toPostResponseDto(post, member);
    }

    public List<PostResponseDto> findAll(String memberId, Member member) {
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        return author.getPosts()
                .stream()
                .map(post -> toPostResponseDto(post, member))
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> getHomePosts(Member member) {
        List<Post> posts = postRepository.findByAuthorInAndCreatedAtGreaterThanEqual(
                followRepository.findFollowingsById(member.getUserId()),
                member.getLastHomeAccessTime());

        member.setLastHomeAccessTime(LocalDateTime.now());

        return posts.stream()
                .map(post -> toPostResponseDto(post, member))
                .collect(Collectors.toList());
    }

    private PostResponseDto toPostResponseDto(Post post, Member member) {
        PostResponseDto postResponseDto = post.postToResponseDto();

        boolean iLiked = postLikeRepository.findByMemberAndPost(member, post).isPresent();
        postResponseDto.setILiked(iLiked);

        return postResponseDto;
    }
}

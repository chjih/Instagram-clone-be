package com.example.InstagramCloneCoding.domain.postlike.application;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.post.domain.Post;
import com.example.InstagramCloneCoding.domain.postlike.dao.PostLikeRepository;
import com.example.InstagramCloneCoding.domain.postlike.domain.PostLike;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.InstagramCloneCoding.domain.post.error.PostErrorCode.POST_NOT_FOUND;
import static com.example.InstagramCloneCoding.domain.postlike.error.PostLikeError.POST_LIKE_EXIST;
import static com.example.InstagramCloneCoding.domain.postlike.error.PostLikeError.POST_LIKE_NOT_FIND;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public void likePost(Member member, int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RestApiException(POST_NOT_FOUND));
        // 이미 좋아요 눌렀는 지 검사
        postLikeRepository.findByMemberAndPost(member, post).ifPresent(postLike -> {
            throw new RestApiException(POST_LIKE_EXIST);
        });

        PostLike postLike = new PostLike(member, post);
        postLikeRepository.save(postLike);
    }

    public void unlikePost(Member member, int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RestApiException(POST_NOT_FOUND));
        // 좋아요 눌러있는 지 검사
        PostLike postLike = postLikeRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new RestApiException(POST_LIKE_NOT_FIND));

        postLikeRepository.delete(postLike);
    }
}
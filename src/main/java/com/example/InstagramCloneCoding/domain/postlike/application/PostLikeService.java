package com.example.InstagramCloneCoding.domain.postlike.application;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.feed.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.feed.domain.Post;
import com.example.InstagramCloneCoding.domain.postlike.dao.PostLikeRepository;
import com.example.InstagramCloneCoding.domain.postlike.domain.PostLike;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.InstagramCloneCoding.domain.feed.error.PostErrorCode.POST_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public void togglePostLike(Member member, int postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new RestApiException(POST_NOT_FOUND));
        PostLike postLike = postLikeRepository.findByMemberAndPost(member, post).orElse(null);

        // 이미 좋아요 누른 상태 -> 삭제
        if(postLike!=null){
            postLikeRepository.delete(postLike);
            return;
        }
        // 좋아요 안누른 상태 -> 추가
        postLikeRepository.save(new PostLike(member, post));
    }
}
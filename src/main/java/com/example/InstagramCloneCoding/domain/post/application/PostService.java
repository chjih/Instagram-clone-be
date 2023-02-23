package com.example.InstagramCloneCoding.domain.post.application;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.dao.PostImageRepository;
import com.example.InstagramCloneCoding.domain.post.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.post.domain.Post;
import com.example.InstagramCloneCoding.domain.post.domain.PostImage;
import com.example.InstagramCloneCoding.domain.post.dto.PostResponseDto;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.InstagramCloneCoding.domain.post.error.PostErrorCode.POST_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostImageRepository postImageRepository;

    public PostResponseDto uploadPost(Member member, String content, List<String> fileNameList) {
        // post 저장
        Post post = new Post(member, content);
        postRepository.save(post);

        // post images 저장
        fileNameList.forEach(fileName -> {
            PostImage postImage = new PostImage(fileName, post);
            postImageRepository.save(postImage);
        });

        return post.postToResponseDto();
    }

    public PostResponseDto findByPostId(int post_id) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new RestApiException(POST_NOT_FOUND));

        return post.postToResponseDto();
    }
}

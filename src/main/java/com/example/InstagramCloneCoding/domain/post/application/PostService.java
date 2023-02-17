package com.example.InstagramCloneCoding.domain.post.application;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.dao.PostImageRepository;
import com.example.InstagramCloneCoding.domain.post.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.post.domain.Post;
import com.example.InstagramCloneCoding.domain.post.domain.PostImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostImageRepository postImageRepository;

    public Post uploadPost(Member member, String content, List<String> fileNameList) {
        // post 저장
        Post post = new Post(member, content);
        postRepository.save(post);

        // post images 저장
        fileNameList.forEach(fileName -> {
            PostImage postImage = new PostImage(fileName, post);
            postImageRepository.save(postImage);
        });

        return post;
    }
}

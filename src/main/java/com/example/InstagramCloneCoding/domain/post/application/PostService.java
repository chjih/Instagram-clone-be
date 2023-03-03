package com.example.InstagramCloneCoding.domain.post.application;

import com.example.InstagramCloneCoding.global.common.aws.AwsS3Service;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.dao.PostImageRepository;
import com.example.InstagramCloneCoding.domain.post.dao.PostRepository;
import com.example.InstagramCloneCoding.domain.post.domain.Post;
import com.example.InstagramCloneCoding.domain.post.domain.PostImage;
import com.example.InstagramCloneCoding.domain.post.dto.PostResponseDto;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.InstagramCloneCoding.domain.post.error.PostErrorCode.POST_NOT_FOUND;
import static com.example.InstagramCloneCoding.global.error.CommonErrorCode.FORBIDDEN;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final AwsS3Service awsS3Service;

    public PostResponseDto uploadPost(Member member, String content, List<MultipartFile> images) {
        // s3 bucket에 이미지 업로드
        List<String> fileNameList = awsS3Service.uploadFile(images);

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

    public void deletePost(Member member, int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(POST_NOT_FOUND));

        // 포스트 작성자인지 확인
        if (!post.getAuthor().equals(member))
            throw new RestApiException(FORBIDDEN);

        // s3 버킷에서 이미지 삭제
        post.getPostImages().forEach(postImage ->
            awsS3Service.deleteFile(postImage.getPostImageId()));
            
        // 포스트 삭제
        postRepository.deleteById(postId);
    }
}

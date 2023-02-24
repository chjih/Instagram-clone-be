package com.example.InstagramCloneCoding.domain.post.dto;

import com.example.InstagramCloneCoding.domain.post.domain.PostImage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter @Setter
public class PostResponseDto {

    private int postId;

    private String authorId;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdAt;

    private List<String> postImages;

    @Builder
    public PostResponseDto(int postId, String authorId, String content, LocalDateTime createdAt, List<PostImage> postImages) {
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.postImages = getPostImagesName(postImages);
    }

    private List<String> getPostImagesName(List<PostImage> postImages) {
        return postImages.stream()
                .map(PostImage::getPostImageId)
                .collect(Collectors.toList());
    }
}
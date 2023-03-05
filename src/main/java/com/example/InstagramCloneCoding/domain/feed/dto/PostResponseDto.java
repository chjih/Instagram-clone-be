package com.example.InstagramCloneCoding.domain.feed.dto;

import com.example.InstagramCloneCoding.domain.feed.domain.PostImage;
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

    private String authorProfileImage;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdAt;

    private List<String> postImages;

    private int likes;

    private boolean iLiked;

    private int commentCount;

    @Builder
    public PostResponseDto(int postId, String authorId, String content, LocalDateTime createdAt,
                           List<PostImage> postImages, int likes, boolean iLiked, String authorProfileImage, int commentCount) {
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.postImages = getPostImagesName(postImages);
        this.likes = likes;
        this.iLiked = iLiked;
        this.authorProfileImage = authorProfileImage;
        this.commentCount = commentCount;
    }

    private List<String> getPostImagesName(List<PostImage> postImages) {
        return postImages.stream()
                .map(PostImage::getPostImageId)
                .collect(Collectors.toList());
    }
}
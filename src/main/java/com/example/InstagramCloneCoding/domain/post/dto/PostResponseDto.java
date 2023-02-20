package com.example.InstagramCloneCoding.domain.post.dto;

import com.example.InstagramCloneCoding.domain.post.domain.PostImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PostResponseDto {

    private int postId;

    private String content;

    private LocalDateTime createdAt;

    private List<String> postImages;
}

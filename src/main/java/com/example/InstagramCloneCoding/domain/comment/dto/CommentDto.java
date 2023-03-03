package com.example.InstagramCloneCoding.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class CommentDto {

    private Integer commentId;

    @NotNull
    private String comment;
}

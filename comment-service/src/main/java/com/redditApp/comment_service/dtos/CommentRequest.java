package com.redditApp.comment_service.dtos;


import lombok.Data;

@Data
public class CommentRequest {

    private Long postId;
    private String content;
}

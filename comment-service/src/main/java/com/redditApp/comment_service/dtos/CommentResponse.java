package com.redditApp.comment_service.dtos;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CommentResponse {

    private Long id;
    private Long postId;
    private Long userId;
    private String content;

    private Instant createdAt;
    private List<CommentResponse> replies;
}

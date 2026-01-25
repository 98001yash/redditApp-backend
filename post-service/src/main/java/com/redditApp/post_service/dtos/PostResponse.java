package com.redditApp.post_service.dtos;


import com.redditApp.post_service.enums.PostType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostResponse {

    private Long id;
    private Long userId;
    private Long subredditId;

    private String title;
    private String content;
    private PostType type;

    private Integer score;
    private Integer commentCount;

    private Instant createdAt;
    private Instant updatedAt;
}

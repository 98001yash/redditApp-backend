package com.redditApp.post_service.event;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostCreatedEvent {

    private Long postId;
    private Long userId;
    private Long subRedditId;
    private Instant createdAt;
}

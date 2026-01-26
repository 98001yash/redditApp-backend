package com.redditApp.feed_service.event;


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

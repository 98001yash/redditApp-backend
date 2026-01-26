package com.redditApp.feed_service.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreatedEvent {

    private Long postId;
    private Long userId;
    private Long subRedditId;
    private Instant createdAt;
}

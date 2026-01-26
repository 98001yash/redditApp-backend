package com.redditApp.feed_service.event;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostDeletedEvent {

    private Long postId;
    private Instant deletedAt;
}

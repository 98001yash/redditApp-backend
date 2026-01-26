package com.redditApp.post_service.event;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostUpdatedEvent {

    private Long postId;
    private String title;
    private String content;
    private Instant updatedAt;
}

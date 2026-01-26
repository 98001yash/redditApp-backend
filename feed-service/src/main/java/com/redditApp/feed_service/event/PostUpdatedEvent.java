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
public class PostUpdatedEvent {

    private Long postId;
    private String title;
    private String content;
    private Instant updatedAt;
}

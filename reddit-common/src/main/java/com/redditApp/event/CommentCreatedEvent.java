package com.redditApp.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreatedEvent {

    private Long commentId;
    private Long postId;

    private Long commenterId;

    private Long postOwnerId;

    private Long parentCommentId;
    private Long parentCommenterOwnerId;

    private String content;

    private Instant createdAt;

}

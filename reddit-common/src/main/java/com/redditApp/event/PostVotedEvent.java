package com.redditApp.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostVotedEvent {

    private Long postId;
    private Long userId;
    private Long newScore;
    private String voteType;
}

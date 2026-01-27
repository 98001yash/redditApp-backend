package com.redditApp.vote_service.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteResponse {


    private Long postId;

    // current score after vote
    private Long score;

    private Integer userVote;
}

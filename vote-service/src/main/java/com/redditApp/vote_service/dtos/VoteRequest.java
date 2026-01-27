package com.redditApp.vote_service.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequest {

    @NotNull
    private Long postId;

    @NotNull
    private boolean upvote;
}

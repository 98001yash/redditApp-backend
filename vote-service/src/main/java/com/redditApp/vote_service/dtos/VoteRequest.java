package com.redditApp.vote_service.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {

    @NotNull
    private Long postId;

    @NotNull
    private boolean upvote;

}

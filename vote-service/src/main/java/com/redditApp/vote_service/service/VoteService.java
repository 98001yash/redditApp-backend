package com.redditApp.vote_service.service;


import com.redditApp.vote_service.dtos.VoteRequest;
import com.redditApp.vote_service.dtos.VoteResponse;

public interface VoteService {



    // Add .  update or remove vote
    VoteResponse vote(VoteRequest voteRequest);


    Long getPostScore(Long postId);
}

package com.redditApp.vote_service.controller;


import com.redditApp.vote_service.dtos.VoteRequest;
import com.redditApp.vote_service.dtos.VoteResponse;
import com.redditApp.vote_service.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;


    @PostMapping
    public ResponseEntity<VoteResponse> vote(
            @Valid @RequestBody VoteRequest request
    ) {

        VoteResponse response =
                voteService.vote(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/score/{postId}")
    public ResponseEntity<Long> getPostScore(
            @PathVariable Long postId
    ) {
        Long score =
                voteService.getPostScore(postId);

        return ResponseEntity.ok(score);
    }
}

package com.redditApp.vote_service.service.Impl;


import com.redditApp.vote_service.auth.UserContextHolder;
import com.redditApp.vote_service.dtos.VoteRequest;
import com.redditApp.vote_service.dtos.VoteResponse;
import com.redditApp.vote_service.entity.Vote;
import com.redditApp.vote_service.enums.VoteType;
import com.redditApp.vote_service.repository.VoteRepository;
import com.redditApp.vote_service.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;


    @Override
    public VoteResponse vote(VoteRequest voteRequest) {

        Long userId = UserContextHolder.getCurrentUserId();

        if (userId == null) {
            throw new SecurityException("Unauthorized");
        }

        Long postId = voteRequest.getPostId();

        VoteType requestedType =
                voteRequest.isUpvote()
                        ? VoteType.UPVOTE
                        : VoteType.DOWNVOTE;

        Optional<Vote> existingOpt =
                voteRepository.findByUserIdAndPostId(userId, postId);

        VoteType finalVote = null;

        if (existingOpt.isEmpty()) {

            Vote vote = Vote.builder()
                    .userId(userId)
                    .postId(postId)
                    .voteType(requestedType)
                    .build();

            voteRepository.save(vote);

            finalVote = requestedType;
        }

        else {
            Vote existing = existingOpt.get();
            // Same vote → remove
            if (existing.getVoteType() == requestedType) {

                voteRepository.delete(existing);
                finalVote = null;
            }
            else {

                existing.setVoteType(requestedType);
                voteRepository.save(existing);

                finalVote = requestedType;
            }
        }

        Long score = voteRepository.getPostScore(postId);

        return VoteResponse.builder()
                .postId(postId)
                .score(score)
                .userVote(
                        (finalVote != null
                                ? finalVote.name()
                                : "NONE")
                )
                .build();
    }


    @Override
    public Long getPostScore(Long postId) {

        Long score = voteRepository.getPostScore(postId);

        return score != null ? score : 0L;
    }

}
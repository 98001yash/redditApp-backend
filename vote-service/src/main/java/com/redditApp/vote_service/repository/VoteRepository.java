package com.redditApp.vote_service.repository;

import com.redditApp.vote_service.entity.Vote;
import com.redditApp.vote_service.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    // find vote by user and post
    Optional<Vote> findByUserIdAndPostId(Long userId, Long postId);

    // delete vote
    void deleteByUserIdAndPostId(Long userId, Long postId);

    // count upvotes
    long countByPostIdAndVoteType(Long postId, VoteType voteType);
}

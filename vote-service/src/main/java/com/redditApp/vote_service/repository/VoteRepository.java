package com.redditApp.vote_service.repository;

import com.redditApp.vote_service.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    // find vote by user and post
    Optional<Vote> findByUserIdAndPostId(Long userId, Long postId);

    // Calculate score using enum
    @Query("""
        SELECT COALESCE(
            SUM(
              CASE 
                WHEN v.voteType = 'UPVOTE' THEN 1
                WHEN v.voteType = 'DOWNVOTE' THEN -1
              END
            ), 0
        )
        FROM Vote v
        WHERE v.postId = :postId
    """)
    Long getPostScore(Long postId);
}

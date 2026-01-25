package com.redditApp.post_service.repository;

import com.redditApp.post_service.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {



    // find active posts
    Optional<Post> findByIdAndIsDeletedFalse(Long id);

    // user posts
    Page<Post> findByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);

    // subreddit feeds
    Page<Post> findBySubredditIdAndIsDeletedFalse(Long subredditId, Pageable pageable);

    // main feed
    Page<Post> findByIsDeletedFalse(Pageable pageable);
}

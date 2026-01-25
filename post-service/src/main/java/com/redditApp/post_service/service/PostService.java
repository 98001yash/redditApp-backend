package com.redditApp.post_service.service;

import com.redditApp.post_service.dtos.CreatePostRequest;
import com.redditApp.post_service.dtos.PostResponse;
import com.redditApp.post_service.dtos.UpdatePostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {


    PostResponse createPost(CreatePostRequest request);

    PostResponse getPostById(Long postId);

    Page<PostResponse> getMainFeed(Pageable pageable);

    Page<PostResponse> getSubredditFeed(Long subredditId, Pageable pageable);

    Page<PostResponse> getUserPosts(Long userId, Pageable pageable);

    PostResponse updatePost(Long postId, UpdatePostRequest request);

    void deletePost(Long postId);
}

package com.redditApp.post_service.controller;


import com.redditApp.post_service.dtos.CreatePostRequest;
import com.redditApp.post_service.dtos.PostResponse;
import com.redditApp.post_service.dtos.UpdatePostRequest;
import com.redditApp.post_service.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping
    public ResponseEntity<PostResponse> createPost
            (@Valid @RequestBody CreatePostRequest request){

        PostResponse response = postService.createPost(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

     //   READ
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id){
        PostResponse response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }
    // Main feed
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getMainFeed(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc")
            String sort
    ) {

        Pageable pageable = buildPageable(page, size, sort);

        Page<PostResponse> feed =
                postService.getMainFeed(pageable);

        return ResponseEntity.ok(feed);
    }

    // Subreddit feed
    @GetMapping("/subreddit/{subredditId}")
    public ResponseEntity<Page<PostResponse>> getSubredditFeed(
            @PathVariable Long subredditId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc")
            String sort
    ) {

        Pageable pageable = buildPageable(page, size, sort);

        Page<PostResponse> feed =
                postService.getSubredditFeed(subredditId, pageable);
        return ResponseEntity.ok(feed);
    }

    // User posts
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostResponse>> getUserPosts(

            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc")
            String sort
    ) {

        Pageable pageable = buildPageable(page, size, sort);
        Page<PostResponse> posts =
                postService.getUserPosts(userId, pageable);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request
    ) {

        PostResponse updated =
                postService.updatePost(id, request);
        return ResponseEntity.ok(updated);
    }

    // ---------------- DELETE ----------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id) {

        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // UTILITY
    private Pageable buildPageable(
            int page,
            int size,
            String sort) {

        String[] parts = sort.split(",");

        String field = parts[0];

        Sort.Direction direction =
                parts.length > 1 &&
                        parts[1].equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        return PageRequest.of(page, size,
                Sort.by(direction, field));
    }
}

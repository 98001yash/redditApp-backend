package com.redditApp.comment_service.controller;


import com.redditApp.comment_service.dtos.CommentRequest;
import com.redditApp.comment_service.dtos.CommentResponse;
import com.redditApp.comment_service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Add comment
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
            @RequestBody CommentRequest request){
        return ResponseEntity.ok(
                commentService.addComment(request)
        );
    }
    // reply
    @PostMapping("/{parentId}/reply")
    public ResponseEntity<CommentResponse> reply(@PathVariable Long parentId,
                                                 @RequestBody CommentRequest request){
        return ResponseEntity.ok(commentService.reply(parentId, request));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<CommentResponse>> getPostComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                commentService.getPostComments(postId, page, size)
        );
    }

    // ---------------- GET REPLIES ----------------
    @GetMapping("/{parentId}/replies")
    public ResponseEntity<List<CommentResponse>> getReplies(
            @PathVariable Long parentId) {

        return ResponseEntity.ok(
                commentService.getReplies(parentId)
        );
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        commentService.delete(id);

        return ResponseEntity.noContent().build();
    }

}

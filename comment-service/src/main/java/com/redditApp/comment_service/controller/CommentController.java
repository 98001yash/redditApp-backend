package com.redditApp.comment_service.controller;


import com.redditApp.comment_service.dtos.CommentRequest;
import com.redditApp.comment_service.dtos.CommentResponse;
import com.redditApp.comment_service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

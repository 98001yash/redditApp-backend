package com.redditApp.comment_service.service;

import com.redditApp.comment_service.dtos.CommentRequest;
import com.redditApp.comment_service.dtos.CommentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    // Add new comment
    CommentResponse addComment(CommentRequest request);

    // reply to comment
    CommentResponse reply(Long parentId, CommentRequest request);

    // delete
    void delete(Long commentId);

    // get comment of post
    Page<CommentResponse> getPostComments(
            Long postId,
            int page,
            int size
    );

    // get replies
    List<CommentResponse> getReplies(Long parentId);
}

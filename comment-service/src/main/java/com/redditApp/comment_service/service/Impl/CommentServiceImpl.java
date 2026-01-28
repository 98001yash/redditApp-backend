package com.redditApp.comment_service.service.Impl;

import com.redditApp.comment_service.auth.UserContextHolder;
import com.redditApp.comment_service.dtos.CommentRequest;
import com.redditApp.comment_service.dtos.CommentResponse;
import com.redditApp.comment_service.entity.Comment;
import com.redditApp.comment_service.repository.CommentRepository;
import com.redditApp.comment_service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentResponse addComment(CommentRequest request) {

        Long userId = UserContextHolder.getCurrentUserId();
        if(userId  == null){
            throw new SecurityException("unauthorized");
        }

        Comment comment = Comment.builder()
                .postId(request.getPostId())
                .userId(userId)
                .content(request.getContent())
                .isDeleted(false)
                .createdAt(Instant.now())
                .build();

        commentRepository.save(comment);
        return mapToResponse(comment);
    }

    @Override
    public CommentResponse reply(Long parentId, CommentRequest request) {
        Long userId = UserContextHolder.getCurrentUserId();

        Comment parent = commentRepository.findByIdAndIsDeletedFalse(parentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found "));

        Comment reply = Comment.builder()
                .postId(parent.getPostId())
                .parentId(parentId)
                .userId(userId)
                .content(request.getContent())
                .isDeleted(false)
                .createdAt(Instant.now())
                .build();

        commentRepository.save(reply);
        return mapToResponse(reply);

    }

    @Override
    public void delete(Long commentId) {
        Long userId = UserContextHolder.getCurrentUserId();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->
                        new RuntimeException("Comment not found"));
        if(!comment.getUserId().equals(userId)){
            throw new SecurityException("Not allowed");
        }

        comment.setIsDeleted(true);
        comment.setUpdatedAt(Instant.now());

        commentRepository.save(comment);
    }

    @Override
    public Page<CommentResponse> getPostComments(Long postId, int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Comment> comments =
                commentRepository
                        .findByPostIdAndParentIdIsNullAndIsDeletedFalse(
                                postId,
                                pageable);

        return comments.map(this::mapToResponse);
    }


    @Override
    public List<CommentResponse> getReplies(Long parentId) {
        return commentRepository
                .findByParentIdAndIsDeletedFalse(parentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // HELPER
    private CommentResponse mapToResponse(Comment comment){

        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

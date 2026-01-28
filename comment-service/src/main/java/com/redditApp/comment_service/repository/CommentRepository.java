package com.redditApp.comment_service.repository;

import com.redditApp.comment_service.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // top level comments of a post (no parent)
    Page<Comment> findByPostIdAndParentIdIsNullAndIsDeletedFalse(
            Long postId, Pageable pageable
    );

    // replies of a comment
    List<Comment> findByParentIdAndIsDeletedFalse(Long parentId);

    // All comments of a post (admin/debug)
    List<Comment> findByPostId(Long postId);

    // find comment by id (not deleted)
    Optional<Comment> findByIdAndIsDeletedFalse(Long id);

    // count comments on post
    Long countByPostIdAndIsDeletedFalse(Long postId);
}

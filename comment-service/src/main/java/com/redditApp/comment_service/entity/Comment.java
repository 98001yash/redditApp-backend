package com.redditApp.comment_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "comments",
        indexes = {
                @Index(name = "idx_post_id", columnList = "post_id"),
                @Index(name = "idx_parent_id", columnList = "parent_id"),
                @Index(name = "idx_user_id", columnList = "user_id")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Post this comment belongs to
    @Column(name = "post_id", nullable = false)
    private Long postId;

    // User who wrote it
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Parent comment (for replies)
    @Column(name = "parent_id")
    private Long parentId;

    // Comment text
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // Score from votes
    @Column(nullable = false)
    private Long score;

    // Soft delete flag
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


    // Auto timestamps
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.score = 0L;
        this.isDeleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}

package com.redditApp.post_service.entity;


import com.redditApp.post_service.enums.PostType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "posts",
        indexes = {
                @Index(name = "idx_posts_user", columnList = "user_id"),
                @Index(name = "idx_posts_subreddit", columnList = "subreddit_id"),
                @Index(name = "idx_posts_created", columnList = "created_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    /* Community */
    @Column(name = "subreddit_id", nullable = false)
    private Long subredditId;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostType type;

    /* Ranking */
    @Column(nullable = false)
    private Integer score = 0;

    @Column(nullable = false)
    private Integer commentCount = 0;

    /* Soft delete */
    @Column(nullable = false)
    private Boolean isDeleted = false;

    /* Auditing */
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    /* Auto timestamps */
    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}

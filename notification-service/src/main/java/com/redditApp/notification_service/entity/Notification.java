package com.redditApp.notification_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // who will receive the Notification
    @Column(name = "user_id",nullable = false)
    private Long userId;

    // VOTE, COMMENT, REPLY
    @Column(nullable = false)
    private String type;

    //display message
     @Column(nullable = false, length = 500)
    private String message;

    // Related postId / commentId
    @Column(name = "reference_id")
    private Long referenceId;

    // Read or not
    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = Instant.now();
        this.isRead = false;
    }
}

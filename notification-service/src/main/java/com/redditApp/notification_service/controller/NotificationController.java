package com.redditApp.notification_service.controller;

import com.redditApp.notification_service.auth.UserContextHolder;
import com.redditApp.notification_service.entity.Notification;
import com.redditApp.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // ---------------- GET ALL ----------------
    @GetMapping
    public ResponseEntity<List<Notification>> getUserNotifications() {

        Long userId = UserContextHolder.getCurrentUserId();

        if (userId == null) {
            throw new SecurityException("Unauthorized");
        }

        List<Notification> notifications =
                notificationService.getUserNotifications(userId);

        return ResponseEntity.ok(notifications);
    }

    // ---------------- GET UNREAD ----------------
    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications() {

        Long userId = UserContextHolder.getCurrentUserId();

        if (userId == null) {
            throw new SecurityException("Unauthorized");
        }

        List<Notification> notifications =
                notificationService.getUnreadNotifications(userId);

        return ResponseEntity.ok(notifications);
    }

    // ---------------- MARK AS READ ----------------
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {

        Long userId = UserContextHolder.getCurrentUserId();

        if (userId == null) {
            throw new SecurityException("Unauthorized");
        }

        notificationService.markAsRead(id, userId);

        return ResponseEntity.ok().build();
    }
}
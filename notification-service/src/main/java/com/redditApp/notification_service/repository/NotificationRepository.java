package com.redditApp.notification_service.repository;

import com.redditApp.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {


    // get all notifications of a user (latest first)
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    // get unread notifications
    List<Notification> findByUserIdAndIsReadFalse(Long userId);
}

package com.redditApp.notification_service.service;

import com.redditApp.notification_service.entity.Notification;

import java.util.List;

public interface NotificationService {

    // create a notification
    void createNotification(Long userId, String message, String type);

    // get all notifications of user
    List<Notification> getUserNotifications(Long userId);

    // get unread notifications
    List<Notification> getUnreadNotifications(Long userId);

    // mark as read
    void markAsRead(Long notificationId, Long userId);
}

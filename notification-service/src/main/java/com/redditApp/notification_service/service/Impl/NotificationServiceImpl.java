package com.redditApp.notification_service.service.Impl;


import com.redditApp.notification_service.entity.Notification;
import com.redditApp.notification_service.repository.NotificationRepository;
import com.redditApp.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void createNotification(Long userId, String message, String type) {

        Notification notification = Notification.builder()
                .userId(userId)
                .message(message)
                .type(type)
                .isRead(false)
                .createdAt(Instant.now())
                .build();

        notificationRepository.save(notification);

    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository
                .findByUserIdAndIsReadFalse(userId);
    }

    @Override
    public void markAsRead(Long notificationId) {


        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(()->new RuntimeException("Notification not found"));

        notification.setRead(true);

        notificationRepository.save(notification);
    }
}

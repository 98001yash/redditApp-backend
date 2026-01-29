package com.redditApp.notification_service.kafka;


import com.redditApp.event.PostVotedEvent;
import com.redditApp.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    // When someone votes on post
    @KafkaListener(
            topics = "post-voted",
            containerFactory = "voteFactory"
    )
    public void consumeVote(PostVotedEvent event) {

        log.info("Received vote event: {}", event);

        String message =
                "Your post received a " + event.getVoteType();

        // Notify post owner (later we can fetch owner via post-service)
        notificationService.createNotification(
                event.getUserId(),
                message,
                "VOTE"
        );
    }

}

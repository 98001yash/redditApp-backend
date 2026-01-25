package com.redditApp.user_service.kafka;


import com.redditApp.user_service.events.UserSignedUpEvent;
import com.redditApp.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSignupConsumer {

    private final UserProfileService userProfileService;

    @KafkaListener(topics = "user.signup", groupId = "user-service")
    public void consume(UserSignedUpEvent event) {
        log.info("Consumed UserSignedUpEvent for userId={}", event.getUserId());
        userProfileService.createProfileIfNotExists(event);
    }
}

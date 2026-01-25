package com.redditApp.auth_service.kafka;


import com.redditApp.auth_service.events.UserSignedUpEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSignedUpProducer {


    private static final String TOPIC = "user.signup";

    private final KafkaTemplate<String, UserSignedUpEvent> kafkaTemplate;

    public void publish(UserSignedUpEvent event) {
        log.info("Publishing UserSignedUpEvent for userId={}", event.getUserId());
        kafkaTemplate.send(TOPIC, event.getUserId().toString(), event);
    }
}

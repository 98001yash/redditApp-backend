package com.redditApp.post_service.kafka;


import com.redditApp.post_service.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "post-events";

    public void publishPostCreated(PostCreatedEvent event) {
        log.info("Publishing Post creation event for postId: {}", event.getPostId());
        kafkaTemplate.send(
                TOPIC,
                event.getPostId().toString(),
                event
        );
    }
}

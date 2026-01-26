package com.redditApp.feed_service.kafka;

import com.redditApp.feed_service.event.PostCreatedEvent;
import com.redditApp.feed_service.event.PostDeletedEvent;
import com.redditApp.feed_service.event.PostUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostEventConsumer {

    private final StringRedisTemplate redisTemplate;

    private static final String MAIN_FEED = "feed:main";

    @KafkaListener(
            topics = "post-created",
            groupId = "feed-service"
    )
    public void consume(PostCreatedEvent event) {

        log.info("Received post: {}", event);

        long score = event.getCreatedAt().toEpochMilli();

        redisTemplate.opsForZSet()
                .add(
                        MAIN_FEED,
                        event.getPostId().toString(),
                        score
                );
    }


    // UPDATE
    @KafkaListener(topics="post-updated", groupId="feed-service")
    public void consumeUpdate(PostUpdatedEvent event){

        // For now: just refresh score (optional)
        redisTemplate.opsForZSet()
                .add(MAIN_FEED,
                        event.getPostId().toString(),
                        event.getUpdatedAt().toEpochMilli());

        log.info("Updated post {} in feed", event.getPostId());
    }

    // DELETE
    @KafkaListener(topics="post-deleted", groupId="feed-service")
    public void consumeDelete(PostDeletedEvent event){

        redisTemplate.opsForZSet()
                .remove(MAIN_FEED,
                        event.getPostId().toString());

        log.info("Removed post {} from feed", event.getPostId());
    }
}

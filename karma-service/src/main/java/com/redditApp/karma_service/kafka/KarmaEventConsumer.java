package com.redditApp.karma_service.kafka;


import com.redditApp.event.PostVotedEvent;
import com.redditApp.karma_service.service.KarmaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KarmaEventConsumer {

    private final KarmaService karmaService;

    @KafkaListener(
            topics = "post-voted",
            groupId = "karma-service"
    )
    public void consume(PostVotedEvent event) {

        log.info("Received vote event: {}", event);

        int points = 0;

        // Decide karma based on vote
        switch (event.getVoteType()) {

            case "UPVOTE" -> points = 1;

            case "DOWNVOTE" -> points = -1;

            case "NONE" -> points = 0;
        }

        karmaService.updateKarma(
                event.getUserId(),
                points
        );

        log.info("Updated karma for user {} by {} points",
                event.getUserId(),
                points);
    }
}

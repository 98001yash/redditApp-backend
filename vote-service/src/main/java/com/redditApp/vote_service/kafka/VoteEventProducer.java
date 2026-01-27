package com.redditApp.vote_service.kafka;


import com.redditApp.event.PostVotedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
public class VoteEventProducer {


    private final KafkaTemplate<String,Object> kafkaTemplate;


    private static final String TOPIC = "post-voted";

    public void publish(PostVotedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getPostId().toString(),
                event
        );
    }
}

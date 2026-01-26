package com.redditApp.post_service.kafka;



import com.redditApp.event.PostCreatedEvent;
import com.redditApp.event.PostDeletedEvent;
import com.redditApp.event.PostUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String CREATED = "post-created";
    private static final String UPDATED = "post-updated";
    private static final String DELETED = "post-deleted";


    public void publishCreated(PostCreatedEvent event){
        kafkaTemplate.send("post-created",
                event.getPostId().toString(), event);
    }

    public void publishUpdated(PostUpdatedEvent event){
        kafkaTemplate.send("post-updated",
                event.getPostId().toString(), event);
    }

    public void publishDeleted(PostDeletedEvent event){
        kafkaTemplate.send("post-deleted",
                event.getPostId().toString(), event);
    }
}

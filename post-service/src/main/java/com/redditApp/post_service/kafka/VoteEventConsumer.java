package com.redditApp.post_service.kafka;


import com.redditApp.event.PostVotedEvent;
import com.redditApp.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteEventConsumer {

    private final PostRepository postRepository;

    public void consume(PostVotedEvent event){

        postRepository.findById(event.getPostId())
                .ifPresent(post-> {
                    post.setScore(Math.toIntExact(event.getNewScore()));

                    postRepository.save(post);

                    log.info("Updated post {} score to {}",
                            event.getPostId(),
                            event.getNewScore());
                });
    }
}

package com.redditApp.karma_service.service.Impl;

import com.redditApp.karma_service.entity.Karma;
import com.redditApp.karma_service.repository.KarmaRepository;
import com.redditApp.karma_service.service.KarmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class KarmaServiceImpl implements KarmaService {

    private final KarmaRepository karmaRepository;

    @Override
    public void updateKarma(Long userId, int points) {

        Karma karma = karmaRepository.findByUserId(userId)
                .orElseGet(()->Karma.builder()
                        .userId(userId)
                        .karma(0L)
                        .createdAt(Instant.now())
                        .build()
                );
        Long newKarma = karma.getKarma() + points;

        // prevent negative karma
        if(newKarma<0){
            newKarma = 0L;
        }

        karma.setKarma(newKarma);
        karma.setUpdatedAt(Instant.now());

        karmaRepository.save(karma);
    }

    @Override
    public Long getKarma(Long userId) {
        Long karma = karmaRepository.getKarmaByUserId(userId);

        return karma !=null ? karma : 0L;
    }
}

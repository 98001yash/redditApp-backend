package com.redditApp.user_service.service;

import com.redditApp.user_service.entities.UserProfile;
import com.redditApp.user_service.events.UserSignedUpEvent;
import com.redditApp.user_service.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    public void createProfileIfNotExists(UserSignedUpEvent event) {

        if (userProfileRepository.existsById(event.getUserId())) {
            log.info("Profile already exists for userId={}", event.getUserId());
            return;
        }

        UserProfile profile = UserProfile.builder()
                .userId(event.getUserId())
                .displayName(event.getDisplayName())
                .avatarUrl(null)
                .build();

        userProfileRepository.save(profile);
        log.info("Profile created for userId={}", event.getUserId());
    }

}

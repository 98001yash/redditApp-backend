package com.redditApp.user_service.service;


import com.redditApp.user_service.dtos.UserProfileCreateRequest;
import com.redditApp.user_service.dtos.UserProfileDto;
import com.redditApp.user_service.entities.UserProfile;
import com.redditApp.user_service.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {


    private final UserProfileRepository userProfileRepository;

    public UserProfileDto createProfile(UserProfileCreateRequest request) {
        log.info("Received request to create profile for userId={}", request.getUserId());

        // Check if profile already exists
        if (userProfileRepository.findById(request.getUserId()).isPresent()) {
            log.warn("Profile already exists for userId={}", request.getUserId());
            throw new RuntimeException("Profile already exists for userId=" + request.getUserId());
        }

        log.info("Creating new UserProfile entity for userId={}", request.getUserId());
        UserProfile profile = UserProfile.builder()
                .userId(request.getUserId())
                .displayName(request.getDisplayName())
                .avatarUrl(request.getAvatarUrl())
                .build();

        UserProfile saved = userProfileRepository.save(profile);
        log.info("UserProfile saved successfully: userId={}, displayName={}, avatarUrl={}",
                saved.getUserId(), saved.getDisplayName(), saved.getAvatarUrl());

        UserProfileDto dto = UserProfileDto.builder()
                .userId(saved.getUserId())
                .displayName(saved.getDisplayName())
                .avatarUrl(saved.getAvatarUrl())
                .build();

        log.info("Returning UserProfileDto for userId={}", dto.getUserId());
        return dto;
    }
}

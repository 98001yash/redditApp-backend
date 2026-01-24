package com.redditApp.user_service.service;

import com.redditApp.user_service.auth.UserContextHolder;
import com.redditApp.user_service.dtos.UserProfileCreateRequest;
import com.redditApp.user_service.dtos.UserProfileDto;
import com.redditApp.user_service.entities.UserProfile;
import com.redditApp.user_service.exceptions.ConflictException;
import com.redditApp.user_service.exceptions.UnauthorizedException;
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

        Long userId = UserContextHolder.getCurrentUserId();

        if (userId == null) {
            log.warn("Unauthorized attempt to create profile");
            throw new UnauthorizedException("User not authenticated");
        }

        if (userProfileRepository.existsById(userId)) {
            log.warn("Profile already exists for userId={}", userId);
            throw new ConflictException("Profile already exists for userId=" + userId);
        }

        log.info("Creating user profile for userId={}", userId);

        UserProfile profile = UserProfile.builder()
                .userId(userId)
                .displayName(request.getDisplayName())
                .avatarUrl(request.getAvatarUrl())
                .build();

        UserProfile saved = userProfileRepository.save(profile);

        log.info("UserProfile created successfully for userId={}", userId);

        return UserProfileDto.builder()
                .userId(saved.getUserId())
                .displayName(saved.getDisplayName())
                .avatarUrl(saved.getAvatarUrl())
                .build();
    }
}

package com.redditApp.user_service.controller;


import com.redditApp.user_service.dtos.UserProfileCreateRequest;
import com.redditApp.user_service.dtos.UserProfileDto;
import com.redditApp.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileDto> createUserProfile(@RequestBody UserProfileCreateRequest request){
        return ResponseEntity.ok(userProfileService.createProfile(request));
    }
}

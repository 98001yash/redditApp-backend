package com.redditApp.auth_service.controller;

import com.redditApp.auth_service.advices.ApiResponse;
import com.redditApp.auth_service.dtos.LoginRequestDto;
import com.redditApp.auth_service.dtos.SignupRequestDto;
import com.redditApp.auth_service.dtos.UserResponseDto;
import com.redditApp.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto){
        log.info("Received signup request for email: {}",signupRequestDto.getEmail());
        UserResponseDto response = authService.signup(signupRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequestDto loginRequest) {
        log.info("Received login request for email: {}", loginRequest.getEmail());
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(new ApiResponse<>(token));
    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        log.info("Fetching current user from token");
        UserResponseDto response = authService.getCurrentUser(authHeader);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }
}

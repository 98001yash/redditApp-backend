package com.redditApp.auth_service.service;

import com.redditApp.auth_service.clients.UserServiceClient;
import com.redditApp.auth_service.dtos.*;
import com.redditApp.auth_service.entity.User;
import com.redditApp.auth_service.enums.Role;
import com.redditApp.auth_service.exceptions.ResourceNotFoundException;
import com.redditApp.auth_service.exceptions.RuntimeConflictException;
import com.redditApp.auth_service.repository.UserRepository;
import com.redditApp.auth_service.utils.PasswordUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final UserServiceClient userServiceClient;

    public UserResponseDto signup(SignupRequestDto signupRequestDto) {
        log.info("Attempting signup for email: {}", signupRequestDto.getEmail());

        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            log.warn("Signup failed. Email already exists: {}", signupRequestDto.getEmail());
            throw new RuntimeConflictException("Email already registered");
        }

        // Save user in Auth DB
        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtils.hashPassword(signupRequestDto.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setLocked(false);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with id: {}", savedUser.getId());

        // Create profile in User-Service
        try {
            UserProfileCreateRequest profileRequest = UserProfileCreateRequest.builder()
                    .displayName(signupRequestDto.getName()) // fallback to name/email
                    .avatarUrl(null)
                    .build();

            log.info("Sending request to User-Service to create profile for userId: {}", savedUser.getId());
            UserProfileDto createdProfile = userServiceClient.createUserProfile(profileRequest);
            log.info("Profile created successfully in User-Service for userId: {}", createdProfile.getUserId());
        } catch (Exception e) {
            log.error("Failed to create profile in User-Service for userId: {}. Error: {}", savedUser.getId(), e.getMessage());
            // Optional: rollback user creation if profile fails
        }

        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login attempt for email: {}", loginRequestDto.getEmail());

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed. User not found for email: {}", loginRequestDto.getEmail());
                    throw new ResourceNotFoundException("Invalid email or password");
                });

        if (!PasswordUtils.checkPassword(loginRequestDto.getPassword(), user.getPassword())) {
            log.warn("Login failed. Invalid password for email: {}", loginRequestDto.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateAccessToken(user);
        log.info("JWT generated successfully for userId: {}", user.getId());

        return token;
    }

    public UserResponseDto getUserById(Long userId) {
        log.info("Fetching user by id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", userId);
                    return new ResourceNotFoundException("User not found with Id: " + userId);
                });

        log.info("User found with id: {}", userId);
        return modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto getCurrentUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Invalid or missing Authorization header");
            throw new BadCredentialsException("Invalid or missing token");
        }

        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        Long userId = claims.get("userId", Long.class);

        log.info("Extracted userId {} from token", userId);
        return getUserById(userId);
    }
}

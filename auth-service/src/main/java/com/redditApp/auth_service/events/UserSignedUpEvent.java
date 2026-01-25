package com.redditApp.auth_service.events;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignedUpEvent {

    private Long userId;
    private String email;
    private String displayName;
    private String role;
    private Instant createdAt;
}

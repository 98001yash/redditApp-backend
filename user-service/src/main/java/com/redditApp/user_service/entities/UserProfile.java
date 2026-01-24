package com.redditApp.user_service.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_profiles")
public class UserProfile {

    private Long userId;
    private String displayName;
    private String avatarUrl;
}

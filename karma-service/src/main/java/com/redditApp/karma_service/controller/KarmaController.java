package com.redditApp.karma_service.controller;


import com.redditApp.karma_service.entity.Karma;
import com.redditApp.karma_service.service.KarmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/karma")
@RequiredArgsConstructor
public class KarmaController {

    private final KarmaService karmaService;

    @GetMapping("/{userId}")
    public ResponseEntity<Karma> getKarma(
            @PathVariable Long userId) {

        Long karma = karmaService.getKarma(userId);

        Karma response = Karma.builder()
                .userId(userId)
                .karma(karma)
                .build();

        return ResponseEntity.ok(response);
    }
}

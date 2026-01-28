package com.redditApp.karma_service.service;

public interface KarmaService {

    void updateKarma(Long userId, int points);

    Long getKarma(Long userId);
}

package com.redditApp.karma_service.repository;

import com.redditApp.karma_service.entity.Karma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KarmaRepository extends JpaRepository<Karma, Long> {

    Optional<Karma> findByUserId(Long userId);

    // get current karma
    @Query("SELECT k.karma FROM Karma k WHERE k.userId = :userId")
    Long getKarmaByUserId(Long userId);
}

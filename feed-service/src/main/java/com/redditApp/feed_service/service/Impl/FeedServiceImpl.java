package com.redditApp.feed_service.service.Impl;


import com.redditApp.feed_service.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;



@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final StringRedisTemplate redisTemplate;
    private static final String MAIN_FEED = "feed:main";

    @Override
    public List<Long> getMainFeed(int page, int size) {
        long start = (long) page*size;
        long end = start+size - 1;

        Set<String> ids = redisTemplate
                .opsForZSet()
                .reverseRange(MAIN_FEED, start, end);

        if(ids == null) return List.of();

        return ids.stream()
                .map(Long::valueOf)
                .toList();
    }
}

package com.redditApp.feed_service.service;

import java.util.List;

public interface FeedService {

    List<Long> getMainFeed(int page, int size);
}

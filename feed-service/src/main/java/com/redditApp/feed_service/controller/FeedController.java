package com.redditApp.feed_service.controller;


import com.redditApp.feed_service.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/main")
    public List<Long> getMainFeed(

            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="20") int size
    ){

        return feedService.getMainFeed(page, size);
    }
}

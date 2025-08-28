package com.snyl.scentanyl.task;

import com.snyl.scentanyl.config.RateLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RateLimitCleanupTask {

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;

    @Scheduled(fixedDelay = 3600000) // Run every hour
    public void cleanup() {
        rateLimitInterceptor.cleanupOldEntries();
    }
}
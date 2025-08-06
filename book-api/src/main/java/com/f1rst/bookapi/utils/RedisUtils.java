package com.f1rst.bookapi.utils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisUtils {

    static final Duration DEFAULT_CACHE_DURATION = Duration.ofMinutes(5);
    final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public <T> T getFromCache(String cacheKey) {
        return (T) redisTemplate.opsForValue().get(cacheKey);
    }

    public <T> void saveToCache(String cacheKey, T data) {
        redisTemplate.opsForValue().set(cacheKey, data, DEFAULT_CACHE_DURATION);
    }
}

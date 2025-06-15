package com.wyl.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LoginRateLimiter {

    private final StringRedisTemplate redisTemplate;
    private static final long WINDOW_SECONDS = 60L;  // 1 分钟窗口
    private static final long MAX_ATTEMPTS   = 5L;   // 最多尝试

    public LoginRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean tryLogin(String username) {
        String key = "login:limit:" + username;
        long now = System.currentTimeMillis();
        // 1. 添加当前时间戳到 ZSET
        redisTemplate.opsForZSet().add(key, String.valueOf(now), now);
        // 2. 删除过期
        redisTemplate.opsForZSet().removeRangeByScore(key, 0, now - WINDOW_SECONDS * 1000);
        // 3. 获取窗口内次数
        Long count = redisTemplate.opsForZSet().zCard(key);
        // 4. 保证 key 过期
        redisTemplate.expire(key, WINDOW_SECONDS, TimeUnit.SECONDS);
        return count != null && count <= MAX_ATTEMPTS;
    }
}

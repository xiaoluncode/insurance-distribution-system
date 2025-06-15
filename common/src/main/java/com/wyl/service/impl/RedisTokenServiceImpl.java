package com.wyl.service.impl;

import com.wyl.service.TokenService;
import com.wyl.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RedisTokenServiceImpl implements TokenService {

    private final StringRedisTemplate redisTemplate;
    private static final long BASE_TTL = 86400L; // 24h 秒
    private static final long JITTER   = 300L;   // 随机抖动 5 分钟

    public RedisTokenServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void storeToken(String jti, Long userId) {
        if (jti == null) {
            throw new IllegalArgumentException("JTI 不能为空！");
        }
        String key = "login:token:" + jti;
        long ttl = BASE_TTL + ThreadLocalRandom.current().nextLong(0, JITTER);
        redisTemplate.opsForValue().set(key, String.valueOf(userId), Duration.ofSeconds(ttl));
    }

    @Override
    public boolean validateToken(String jti) {
        String key = "login:token:" + jti;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void revokeToken(String jti) {
        String key = "login:token:" + jti;
        redisTemplate.delete(key);
    }
}

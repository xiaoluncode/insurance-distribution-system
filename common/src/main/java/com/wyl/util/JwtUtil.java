package com.wyl.util;

import com.wyl.vo.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Data
@Component
public class JwtUtil {

    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder()
            .decode("a2Fkc2xmbmphZGZramFkZmFzZGprZm5zZGtmamRmYWRqZmthbGRmamY="));


    @Value("${jwt.expiration-ms:604800000}") // 默认7天
    private  long expiration;

    // 生成 token
    public JwtToken createToken(Long userId, String role) {
        Instant now = Instant.now();
        String jti = UUID.randomUUID().toString();
        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .setId(jti) // ✅ 设置 jti
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expiration, ChronoUnit.MILLIS)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return new JwtToken(token, jti);
    }

    // 解析 token
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
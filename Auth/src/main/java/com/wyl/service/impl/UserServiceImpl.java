package com.wyl.service.impl;

import com.wyl.dto.LoginDTO;
import com.wyl.dto.RegisterDTO;
import com.wyl.entity.User;
import com.wyl.exception.BusinessException;
import com.wyl.mapper.UserMapper;
import com.wyl.service.TokenService;
import com.wyl.service.UserService;
import com.wyl.util.JwtUtil;
import com.wyl.util.LoginRateLimiter;
import com.wyl.vo.JwtToken;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final LoginRateLimiter rateLimiter;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void register(RegisterDTO dto) {
        if (userMapper.selectByUsername(dto.getUsername()) != null) {
            throw new BusinessException(400, "用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        userMapper.insert(user);
    }

    @Override
    public String login(LoginDTO dto) {
        // 1. 限流
        if (!rateLimiter.tryLogin(dto.getUsername())) {
            throw new BusinessException(429, "尝试次数过多，请稍后再试");
        }
        // 2. 验证
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        // 3. 生成 JWT 并提取 jti
        JwtToken jwtToken = jwtUtil.createToken(user.getId(), user.getRole());
        System.out.println("JTI=" + jwtToken.getJti()); //
        tokenService.storeToken(jwtToken.getJti(), user.getId());
        return jwtToken.getToken();
    }
}

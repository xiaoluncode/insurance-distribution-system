package com.wyl.service;

public interface TokenService {
    void storeToken(String jti, Long userId);
    boolean validateToken(String jti);
    void revokeToken(String jti);
}


package com.globallogic.user.infrastructure.utils.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
public class JWTUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public SecretKey getKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Key length is insufficient. It must be at least 256 bits.");
        }

        return new SecretKeySpec(keyBytes, 0, 32, "HmacSHA256");
    }

}

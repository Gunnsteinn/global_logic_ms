package com.globallogic.user.infrastructure.utils.jwt;

import com.globallogic.user.infrastructure.persistence.entities.UserDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Clase utilitaria para la generación y validación de tokens JWT.
 */
@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.expiration}")
    private Long JWT_EXPIRATION;

    public String generateToken(String email) {
        return Jwts
                .builder()
                .header()
                .type("JWT")
                .and()
                .subject(email)
                .claims(generateClaims())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String getUsernameToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public SecretKey getKey() {
        byte[] keyBytes = JWT_SECRET.getBytes();
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Key length is insufficient. It must be at least 256 bits.");
        }

        return new SecretKeySpec(keyBytes, 0, 32, "HmacSHA256");
    }

    public Map<String, Object> generateClaims() {
        return new HashMap<>();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims payload = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claimsResolver.apply(payload);
    }
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        return getClaim(token, Claims::getExpiration).before(new Date());
    }
}

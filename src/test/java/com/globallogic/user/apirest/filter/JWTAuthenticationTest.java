package com.globallogic.user.apirest.filter;

import com.globallogic.user.infrastructure.utils.jwt.JWTUtils;
import io.jsonwebtoken.security.Keys;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JWTAuthenticationTest {
    private final EasyRandom easyRandom = new EasyRandom();
    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private JWTAuthentication jwtAuthentication;

    @BeforeEach
    void setUp() {
        String jwtSecret = "global123456789LOGIC987654321global";
        ReflectionTestUtils.setField(jwtAuthentication, "jwtSecret", jwtSecret);
        long tokenExpirationTime = 30000;
        ReflectionTestUtils.setField(jwtAuthentication, "tokenExpirationTime", tokenExpirationTime);
        String tokenBearerPrefix = "Bearer ";
        ReflectionTestUtils.setField(jwtAuthentication, "jwtSecret", tokenBearerPrefix);

    }

    @Test
    void shouldReturnOkWhenGetJWTToken() {
        String username = easyRandom.nextObject(String.class);
        SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        lenient().when(jwtUtils.getKey()).thenReturn(secretKey);

        String jwtToken = jwtAuthentication.getJWTToken(username);

        assertEquals(String.class, jwtToken.getClass());
    }

    @Test
    void shouldThrowExceptionWhenUsernameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> jwtAuthentication.getJWTToken(null));
    }

    @Test
    void shouldThrowExceptionWhenJwtSecretIsNullOrEmpty() {
        ReflectionTestUtils.setField(jwtAuthentication, "jwtSecret", "");

        assertThrows(IllegalArgumentException.class, () -> jwtAuthentication.getJWTToken("username"));
    }

    @Test
    void shouldThrowExceptionWhenTokenExpirationTimeIsZero() {
        ReflectionTestUtils.setField(jwtAuthentication, "tokenExpirationTime", 0);

        assertThrows(IllegalArgumentException.class, () -> jwtAuthentication.getJWTToken("username"));
    }

    @Test
    void shouldThrowExceptionWhenTokenGenerationFails() {
        String username = easyRandom.nextObject(String.class);
        when(jwtUtils.getKey()).thenThrow(new RuntimeException("Error generating token"));

        assertThrows(RuntimeException.class, () -> jwtAuthentication.getJWTToken(username));
    }
}
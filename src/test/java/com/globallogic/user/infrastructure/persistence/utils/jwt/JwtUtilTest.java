package com.globallogic.user.infrastructure.persistence.utils.jwt;

import com.globallogic.user.infrastructure.utils.jwt.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private JWTUtils jwtUtil;

    private final String jwtSecret = "global123456789LOGIC987654321global";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", jwtSecret);
    }

    @Test
    void shouldGenerateToken() {
        byte[] keyBytes = jwtSecret.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyBytes, 0, 32, "HmacSHA256");
        lenient().when(jwtUtils.getKey()).thenReturn(secretKey);

        SecretKey returnedKey = jwtUtil.getKey();

        assertEquals(secretKey, returnedKey);
    }

    @Test
    void shouldThrowExceptionIfKeyLengthIsInsufficient() {
        String shortKey = "shortKey";
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", shortKey);

        assertThrows(IllegalArgumentException.class, () -> jwtUtil.getKey());
    }
}

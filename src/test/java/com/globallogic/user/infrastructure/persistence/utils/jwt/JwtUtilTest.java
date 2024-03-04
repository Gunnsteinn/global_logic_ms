package com.globallogic.user.infrastructure.persistence.utils.jwt;


import com.globallogic.user.infrastructure.utils.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private JwtUtil jwtUtilMock;

    @Test
    void shouldGenerateToken() {
        String email = "test@example.com";
        ReflectionTestUtils.setField(jwtUtilMock, "JWT_SECRET", "global123456789LOGIC987654321global");
        ReflectionTestUtils.setField(jwtUtilMock, "JWT_EXPIRATION", 300000L); // 1 hour

        String token = jwtUtilMock.generateToken(email);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenKeyLengthIsInsufficient() {

        ReflectionTestUtils.setField(jwtUtilMock, "JWT_SECRET", "shortSecret");
        assertThrows(IllegalArgumentException.class, jwtUtilMock::getKey);
    }

}

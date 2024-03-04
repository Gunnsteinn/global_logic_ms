package com.globallogic.user.application.business;

import com.globallogic.user.application.ports.output.UserOutputPort;
import com.globallogic.user.domain.exception.ApiException;
import com.globallogic.user.domain.model.User;
import com.globallogic.user.infrastructure.utils.jwt.JwtUtil;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private UserOutputPort userOutputPort;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    void shouldReturnOkWhenRegisterUserHaveGodParams() {
        var user = easyRandom.nextObject(User.class);
        var time = easyRandom.nextObject(ZonedDateTime.class);
        var token = easyRandom.nextObject(String.class);
        user.setCreated(time);
        user.setModified(time);
        user.setLastLogin(time);
        user.setToken(token);
        when(jwtUtil.generateToken(user.getEmail())).thenReturn(token);
        when(userOutputPort.saveUser(user)).thenReturn(user);
        var response = userServiceImpl.register(user);

        assertThat(response.getToken()).isEqualTo(token);
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldReturnApiExceptionWhenRegisterUserIsRepeated() {
        var user = easyRandom.nextObject(User.class);
        when(userOutputPort.existsByUser(user.getEmail())).thenReturn(true);

        assertThatExceptionOfType(ApiException.class)
                .isThrownBy(() -> userServiceImpl.register(user))
                .withMessage("El correo ya fué registrado")
                .withNoCause();
    }

}

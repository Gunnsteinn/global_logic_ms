package com.globallogic.user.application.business;

import com.globallogic.user.apirest.filter.JWTAuthentication;
import com.globallogic.user.application.ports.output.UserOutputPort;
import com.globallogic.user.domain.exception.ApiException;
import com.globallogic.user.domain.model.User;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private UserOutputPort userOutputPort;

    @Mock
    private JWTAuthentication JWTAuthentication;

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
        when(JWTAuthentication.getJWTToken(user.getEmail())).thenReturn(token);
        when(userOutputPort.saveUser(user)).thenReturn(user);
        var response = userServiceImpl.register(user);

        assertThat(response.getToken()).isEqualTo(token);
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldReturnOkWhenRegisterUserWithValidParams() {
        User user = easyRandom.nextObject(User.class);
        String token = "generatedToken";
        when(JWTAuthentication.getJWTToken(user.getEmail())).thenReturn(token);
        when(userOutputPort.existsByUser(user.getEmail())).thenReturn(false);
        when(userOutputPort.saveUser(user)).thenReturn(user);

        User registeredUser = userServiceImpl.register(user);

        assertThat(registeredUser.getToken()).isEqualTo(token);
        assertThat(registeredUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(registeredUser.getCreated()).isNotNull();
        assertThat(registeredUser.getModified()).isNotNull();
        assertThat(registeredUser.getLastLogin()).isNotNull();
        assertThat(registeredUser.isActive()).isTrue();
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

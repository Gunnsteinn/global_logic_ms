package com.globallogic.user.application.business;

import com.globallogic.user.apirest.filter.JWTAuthentication;
import com.globallogic.user.application.ports.output.UserOutputPort;
import com.globallogic.user.domain.business.UserService;
import com.globallogic.user.domain.exception.ApiException;
import com.globallogic.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserOutputPort userOutputPort;
    private final JWTAuthentication JWTAuthentication;


    @Override
    public User register(User user) {
        if (userOutputPort.existsByUser(user.getEmail())) {
            throw new ApiException("El correo ya fué registrado", HttpStatus.BAD_REQUEST.value());
        }

        ZonedDateTime time = ZonedDateTime.now();
        user.setCreated(time);
        user.setModified(time);
        user.setLastLogin(time);
        user.setToken(JWTAuthentication.getJWTToken(user.getEmail()));

        user.setActive(true);
        return userOutputPort.saveUser(user);
    }
}

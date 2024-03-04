package com.globallogic.user.apirest;

import com.globallogic.user.apirest.dto.input.RegisterDTO;
import com.globallogic.user.apirest.dto.output.UserDTO;
import com.globallogic.user.apirest.mapper.UserDTOMapper;
import com.globallogic.user.domain.business.UserService;
import com.globallogic.user.domain.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private UserService userService;
    @Mock
    private UserDTOMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @InjectMocks
    private AuthController authController;


    @Test
    void shouldReturnOkWhenRegisterUser() {
        var user = easyRandom.nextObject(User.class);
        var registerDto = easyRandom.nextObject(RegisterDTO.class);
        var userDto = easyRandom.nextObject(UserDTO.class);

        when(userService.register(user)).thenReturn(user);
        when(mapper.convertToDomain(registerDto)).thenReturn(user);
        when(mapper.convertToDTO(user)).thenReturn(userDto);
        var response = authController.register(registerDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(userDto);
    }

    @Test
    void shouldReturnWhenPaymentExists() {
        var registerDto = easyRandom.nextObject(RegisterDTO.class);
        registerDto.setEmail("juanrodriguez.org");
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDto);

        assertFalse(violations.isEmpty());
    }
}

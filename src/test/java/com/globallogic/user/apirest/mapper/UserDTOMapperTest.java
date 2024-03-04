package com.globallogic.user.apirest.mapper;

import com.globallogic.user.apirest.dto.input.RegisterDTO;
import com.globallogic.user.apirest.dto.output.UserDTO;
import com.globallogic.user.domain.model.User;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDTOMapperTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserDTOMapper userDTOMapper;


    @Test
    void shouldMapDTOToDomainObject() {

        var registerDTO = easyRandom.nextObject(RegisterDTO.class);
        var expectedUser = easyRandom.nextObject(User.class);

        when(modelMapper.map(registerDTO, User.class)).thenReturn(expectedUser);
        User actualUser = userDTOMapper.convertToDomain(registerDTO);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void shouldMapDomainObjectToDTO() {

        var user = easyRandom.nextObject(User.class);
        var expectedUserDTO = easyRandom.nextObject(UserDTO.class);

        when(modelMapper.map(user, UserDTO.class)).thenReturn(expectedUserDTO);
        UserDTO actualUserDTO = userDTOMapper.convertToDTO(user);

        assertEquals(expectedUserDTO, actualUserDTO);
    }
}

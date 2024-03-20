package com.globallogic.user.infrastructure.persistence.mapper;

import com.globallogic.user.domain.model.User;
import com.globallogic.user.infrastructure.persistence.entities.UserDAO;
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
class UserDAOMapperTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserDAOMapper userDAOMapper;

    @Test
    void shouldMapDAOToDomainObject() {
        var userDAO = easyRandom.nextObject(UserDAO.class);
        var expectedUser = easyRandom.nextObject(User.class);

        when(modelMapper.map(userDAO, User.class)).thenReturn(expectedUser);
        User actualUser = userDAOMapper.convertToDomain(userDAO);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void shouldMapDomainObjectToDAO() {
        var user = easyRandom.nextObject(User.class);
        var expectedUserDAO = easyRandom.nextObject(UserDAO.class);

        when(modelMapper.map(user, UserDAO.class)).thenReturn(expectedUserDAO);
        UserDAO actualUserDAO = userDAOMapper.convertToDAO(user);

        assertEquals(expectedUserDAO, actualUserDAO);
    }
}

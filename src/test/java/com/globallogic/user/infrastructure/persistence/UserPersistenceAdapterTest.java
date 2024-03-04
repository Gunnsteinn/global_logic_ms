package com.globallogic.user.infrastructure.persistence;

import com.globallogic.user.domain.model.User;
import com.globallogic.user.infrastructure.persistence.entities.UserDAO;
import com.globallogic.user.infrastructure.persistence.mapper.UserDAOMapper;
import com.globallogic.user.infrastructure.persistence.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDAOMapper userDAOMapper;

    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

    @Test
    void shouldSaveUserAndReturnDomainUser() {

        var user = easyRandom.nextObject(User.class);
        var userDAO = easyRandom.nextObject(UserDAO.class);

        when(userDAOMapper.convertToDAO(user)).thenReturn(userDAO);
        when(userRepository.save(userDAO)).thenReturn(userDAO);
        when(userDAOMapper.convertToDomain(userDAO)).thenReturn(user);
        User savedUser = userPersistenceAdapter.saveUser(user);

        assertEquals(user, savedUser);
    }

    @Test
    void shouldCheckIfUserExistsByEmail() {
        var email = "aaaaaaa@dominio.cl";

        when(userRepository.existsByEmail(email)).thenReturn(true);
        boolean exists = userPersistenceAdapter.existsByUser(email);

        assertTrue(exists);
    }
}

package com.globallogic.user.infrastructure.persistence;

import com.globallogic.user.application.ports.output.UserOutputPort;
import com.globallogic.user.domain.model.User;
import com.globallogic.user.infrastructure.persistence.entities.UserDAO;
import com.globallogic.user.infrastructure.persistence.mapper.UserDAOMapper;
import com.globallogic.user.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserPersistenceAdapter implements UserOutputPort {
    private final UserRepository userRepository;
    private final UserDAOMapper userDAOMapper;

    @Override
    public User saveUser(User user) {
        UserDAO productEntity = userDAOMapper.convertToDAO(user);
        UserDAO userDAO = userRepository.save(productEntity);
        return userDAOMapper.convertToDomain(userDAO);
    }

    @Override
    public boolean existsByUser(String email) {
        return userRepository.existsByEmail(email);
    }
}
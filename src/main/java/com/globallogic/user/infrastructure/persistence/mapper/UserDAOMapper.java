package com.globallogic.user.infrastructure.persistence.mapper;


import com.globallogic.user.domain.model.User;
import com.globallogic.user.infrastructure.persistence.entities.UserDAO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDAOMapper {
    private final ModelMapper modelMapper;

    public UserDAO convertToDAO(User user) {
        UserDAO userDAO = modelMapper.map(user, UserDAO.class);
        userDAO.getPhones().forEach(phone -> phone.setUser(userDAO));
        return userDAO;
    }

    public User convertToDomain(UserDAO userDAO) {
        return modelMapper.map(userDAO, User.class);
    }
}

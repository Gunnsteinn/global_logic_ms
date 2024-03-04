package com.globallogic.user.apirest.mapper;


import com.globallogic.user.apirest.dto.input.RegisterDTO;
import com.globallogic.user.apirest.dto.output.UserDTO;
import com.globallogic.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDTOMapper {
    private final ModelMapper modelMapper;

    public User convertToDomain(RegisterDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.getPhones().forEach(phone -> phone.setUser(user));
        return user;
    }

    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}

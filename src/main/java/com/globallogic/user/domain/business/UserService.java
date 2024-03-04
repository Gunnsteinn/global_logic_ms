package com.globallogic.user.domain.business;


import com.globallogic.user.domain.model.User;

import java.util.Optional;

public interface UserService {
    User register(User entity);
}



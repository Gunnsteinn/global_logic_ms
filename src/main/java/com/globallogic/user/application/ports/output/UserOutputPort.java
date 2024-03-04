package com.globallogic.user.application.ports.output;

import com.globallogic.user.domain.model.User;

public interface UserOutputPort {
    User saveUser(User user);

    boolean existsByUser(String email);
}

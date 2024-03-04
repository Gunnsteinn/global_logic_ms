package com.globallogic.user.infrastructure.persistence.repository;

import com.globallogic.user.infrastructure.persistence.entities.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {
    Optional<UserDAO> findByEmail(String email);

    boolean existsByEmail(String email);
}

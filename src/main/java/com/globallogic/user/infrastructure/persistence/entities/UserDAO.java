package com.globallogic.user.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "app_user")
@ToString
public class UserDAO {
    private static final String MAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final java.util.regex.Pattern REGEX = java.util.regex.Pattern.compile(MAIL_REGEX);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneDAO> phones;
    private String token;

    private boolean isActive;
    private ZonedDateTime lastLogin;
    private ZonedDateTime modified;
    private ZonedDateTime created;

}

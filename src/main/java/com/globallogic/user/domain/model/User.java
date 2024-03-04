package com.globallogic.user.domain.model;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;


@Data
public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
    private String token;
    private boolean isActive;
    private ZonedDateTime lastLogin;
    private ZonedDateTime modified;
    private ZonedDateTime created;
}

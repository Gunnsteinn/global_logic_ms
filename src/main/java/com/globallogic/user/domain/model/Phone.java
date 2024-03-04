package com.globallogic.user.domain.model;

import lombok.Data;

@Data
public class Phone {
    private long id;
    private String number;
    private String cityCode;
    private String countryCode;
    private User user;
}

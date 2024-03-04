package com.globallogic.user.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@ToString
public class PhoneDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String number;
    private String cityCode;
    private String countryCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserDAO.class)
    @JoinColumn(name = "phones", nullable = true)
    private UserDAO user;

}

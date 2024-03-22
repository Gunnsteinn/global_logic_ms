package com.globallogic.user.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "phones")
@ToString
public class PhoneDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    private String cityCode;
    private String countryCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserDAO.class)
    @JoinColumn(name = "user_id", nullable = true)
    private UserDAO user;

}

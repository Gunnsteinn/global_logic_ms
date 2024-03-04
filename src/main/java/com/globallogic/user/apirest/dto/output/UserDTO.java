package com.globallogic.user.apirest.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private List<PhoneDTO> phones;
    private ZonedDateTime created;
    private ZonedDateTime modified;
    @JsonProperty("last_login")
    private ZonedDateTime lastLogin;
    private String token;
    @JsonProperty("isactive")
    private boolean isActive;
}

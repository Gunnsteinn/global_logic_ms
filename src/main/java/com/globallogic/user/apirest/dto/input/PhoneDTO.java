package com.globallogic.user.apirest.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
    @Schema(description = "User phone number", example = "1234567")
    private String number;
    @Schema(description = "User city code", example = "1")
    @JsonProperty("citycode")
    private String cityCode;
    @Schema(description = "User country code", example = "57")
    @JsonProperty("contrycode")
    private String countryCode;
}

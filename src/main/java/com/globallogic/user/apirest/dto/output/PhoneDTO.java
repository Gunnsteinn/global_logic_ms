package com.globallogic.user.apirest.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneDTO {
    private String number;
    private String cityCode;
    private String countryCode;
}

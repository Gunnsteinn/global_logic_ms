package com.globallogic.user.apirest.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @Schema(description = "User name", example = "Juan Rodriguez")
    private String name;
    @Schema(description = "User email", example = "aaaaaaa@dominio.cl")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email inválido")
    private String email;
    @Schema(description = "User password", example = "hunter2")
    @Pattern(regexp = "^(?=.*[a-zA-Z]{5,9})(?=.*\\d{1,5})[a-zA-Z\\d]+$", message = "Password inválido")
    private String password;
    private List<PhoneDTO> phones;
}
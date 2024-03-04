package com.globallogic.user.apirest;

import com.globallogic.user.apirest.dto.input.RegisterDTO;
import com.globallogic.user.apirest.dto.output.UserDTO;
import com.globallogic.user.apirest.mapper.UserDTOMapper;
import com.globallogic.user.domain.business.UserService;
import com.globallogic.user.domain.exception.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final UserService userService;
    private final UserDTOMapper mapper;

    @Operation(summary = "Create a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "user status created"),
            @ApiResponse(responseCode = "400", description = "When invalid user is received",
                    content = {@Content(schema = @Schema(implementation = ApiException.class))})
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterDTO userDTO) {
        UserDTO user = mapper.convertToDTO(userService.register(mapper.convertToDomain(userDTO)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }
}


package com.globallogic.user.apirest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class HelloController {

    @Operation(
            summary = "Get a greeting",
            parameters = {
                    @Parameter(name = "Authorization",
                            description = "Authorization Bearer token",
                            required = true, in = ParameterIn.HEADER, schema = @Schema(type = "string"),
                            example = "Bearer YOUR_ACCESS_TOKEN")
            }
    )
    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "AUTHENTICATED");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
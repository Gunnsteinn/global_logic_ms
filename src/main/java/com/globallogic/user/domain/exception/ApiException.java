package com.globallogic.user.domain.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ApiException extends RuntimeException {
    @JsonProperty("mensaje")
    private String message;
    private int statusCode;

}

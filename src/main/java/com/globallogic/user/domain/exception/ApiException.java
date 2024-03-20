package com.globallogic.user.domain.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ApiException extends RuntimeException {
    @JsonProperty("mensaje")
    private final String message;
    private final int statusCode;

}

package com.globallogic.user.apirest;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class HelloControllerTest {

    private final EasyRandom easyRandom = new EasyRandom();


    @InjectMocks
    private HelloController helloController;


    @Test
    void shouldReturnOkWhenRegisterUser() {
        ResponseEntity<Map<String, String>> response = helloController.hello();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        Map<String, String> responseBody = response.getBody();
        assertThat(responseBody)
                .containsEntry("status", "AUTHENTICATED");
    }
}
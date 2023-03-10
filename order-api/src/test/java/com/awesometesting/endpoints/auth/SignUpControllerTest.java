package com.awesometesting.endpoints.auth;

import com.awesometesting.DomainHelper;
import com.awesometesting.dto.auth.SignUpRequest;
import com.awesometesting.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ConstantConditions")
public class SignUpControllerTest extends DomainHelper {

    @Test
    public void shouldRegister() {
        // given
        SignUpRequest signUpRequest = UserUtil.getRandomUser();

        // when
        ResponseEntity<?> response = registerUser(signUpRequest, Object.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().toString()).isNotBlank();
    }

    @Test
    public void shouldFailToRegisterExistingUsername() {
        // given
        SignUpRequest firstUser = UserUtil.getRandomUser();
        registerUser(firstUser, Object.class);
        SignUpRequest secondUser = UserUtil.getRandomUserWithUsername(firstUser.getUsername());

        // when
        ResponseEntity<?> response = registerUser(secondUser, Object.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void shouldFailToRegisterUsernameTooShort() {
        // given
        SignUpRequest user = UserUtil.getRandomUserWithUsername("o");

        // when
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                REGISTER_ENDPOINT,
                HttpMethod.POST,
                new HttpEntity<>(user, getJsonOnlyHeaders()),
                new ParameterizedTypeReference<>() {
                });

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private <T> ResponseEntity<T> registerUser(SignUpRequest signUpRequest, Class<T> clazz) {
        return executePost(
                REGISTER_ENDPOINT,
                signUpRequest,
                getJsonOnlyHeaders(),
                clazz);
    }
}

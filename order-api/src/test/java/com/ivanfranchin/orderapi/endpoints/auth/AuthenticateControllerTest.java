package com.ivanfranchin.orderapi.endpoints.auth;

import com.ivanfranchin.orderapi.DomainHelper;
import com.ivanfranchin.orderapi.rest.dto.AuthResponse;
import com.ivanfranchin.orderapi.rest.dto.LoginRequest;
import com.ivanfranchin.orderapi.rest.dto.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.ivanfranchin.orderapi.util.UserUtil.getRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticateControllerTest extends DomainHelper {

    private String validUsername;
    private String validPassword;

    @BeforeEach
    public void prepareUserForTest() {
        SignUpRequest user = getRandomUser();
        validUsername = user.getUsername();
        validPassword = user.getPassword();
        registerAndGetToken(user);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldLoginUser() {
        // when
        ResponseEntity<AuthResponse> responseWithToken =
                attemptLogin(new LoginRequest(validUsername, validPassword), AuthResponse.class);

        // then
        assertThat(responseWithToken.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseWithToken.getBody().accessToken()).isNotBlank();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldReturn400IfUsernameOrPasswordTooShort() {
        // when
        ResponseEntity<?> response = restTemplate.exchange(
                LOGIN_ENDPOINT,
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest("2", "tw"), getJsonOnlyHeaders()),
                Object.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldReturn401OnWrongPassword() {
        // when
        ResponseEntity<?> responseWithToken =
                attemptLogin(new LoginRequest(validUsername, "wrong"), Object.class);

        // then
        assertThat(responseWithToken.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldReturn401OnWrongUsername() {
        // when
        ResponseEntity<?> responseWithToken =
                attemptLogin(new LoginRequest("wrong", validPassword), Object.class);

        // then
        assertThat(responseWithToken.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}

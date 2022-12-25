package com.ivanfranchin.orderapi.endpoints.users;

import com.ivanfranchin.orderapi.DomainHelper;
import com.ivanfranchin.orderapi.rest.dto.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.ivanfranchin.orderapi.util.UserUtil.getRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserControllerTest extends DomainHelper {

    @Test
    public void shouldDeleteUserAsAdmin() {
        // given
        SignUpRequest user = getRandomUser();
        registerUser(user);
        String username = user.getUsername();
        String apiToken = loginAsAdminAndGetToken();

        // when
        ResponseEntity<String> response =
                executeDelete(getUserEndpoint(username), getHeadersWith(apiToken), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldGet403AsClient() {
        // given
        SignUpRequest user = getRandomUser();
        registerUser(user);
        String username = user.getUsername();
        String apiToken = registerAndGetToken(getRandomUser());

        // when
        ResponseEntity<?> response =
                executeDelete(getUserEndpoint(username),
                        getHeadersWith(apiToken));

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void shouldGet401AsUnauthorized() {
        // given
        SignUpRequest user = getRandomUser();
        registerUser(user);
        String username = user.getUsername();

        // when
        ResponseEntity<?> response =
                executeDelete(getUserEndpoint(username),
                        getJsonOnlyHeaders());

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldGet404Nonexisting() {
        // given
        String apiToken = loginAsAdminAndGetToken();


        // when
        ResponseEntity<?> response =
                executeDelete(getUserEndpoint("nonexisting"),
                        getHeadersWith(apiToken));

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}

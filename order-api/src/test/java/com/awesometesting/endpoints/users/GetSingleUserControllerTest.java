package com.awesometesting.endpoints.users;

import com.awesometesting.DomainHelper;
import com.awesometesting.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.awesometesting.util.UserUtil.getRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

public class GetSingleUserControllerTest extends DomainHelper {

    @Test
    public void shouldGetUserAsAdmin() {
        // given
        String adminToken = loginAsAdminAndGetToken();

        // when
        ResponseEntity<UserDto> response =
                executeGet(getUserEndpoint(ADMIN),
                        getHeadersWith(adminToken),
                        UserDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldGet403AsClient() {
        // given
        String clientToken = registerAndGetToken(getRandomUser());

        // when
        ResponseEntity<?> response =
                executeGet(getUserEndpoint(ADMIN),
                        getHeadersWith(clientToken));

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void shouldGet401AsNotLoggedIn() {

        // when
        ResponseEntity<?> userResponseEntity = restTemplate.exchange(
                getUserEndpoint(ADMIN),
                HttpMethod.GET,
                new HttpEntity<>(getJsonOnlyHeaders()),
                Object.class);

        // then
        assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldGet404ForNonExistingUser() {
        // given
        String token = loginAsAdminAndGetToken();

        // when
        ResponseEntity<?> response =
                executeGet(getUserEndpoint("nonexisting"),
                        getHeadersWith(token));

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}

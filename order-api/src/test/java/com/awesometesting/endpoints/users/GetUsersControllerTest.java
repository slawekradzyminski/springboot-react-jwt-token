package com.awesometesting.endpoints.users;

import com.awesometesting.DomainHelper;
import com.awesometesting.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.awesometesting.util.UserUtil.getRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

public class GetUsersControllerTest extends DomainHelper {

    @Test
    public void shouldGetUsersAsAdmin() {
        // given
        String adminToken = loginAsAdminAndGetToken();

        // when
        ResponseEntity<UserDto[]> response =
                executeGet(USERS_ENDPOINT,
                        getHeadersWith(adminToken),
                        UserDto[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSizeGreaterThan(0);
    }

    @Test
    public void shouldGet403AsUser() {
        // given
        String adminToken = registerAndGetToken(getRandomUser());

        // when
        ResponseEntity<?> response =
                executeGet(USERS_ENDPOINT,
                        getHeadersWith(adminToken));

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void shouldGet401AsUnauthorized() {
        // when
        ResponseEntity<?> userResponseEntity = executeGet(
                USERS_ENDPOINT,
                getJsonOnlyHeaders());

        // then
        assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}

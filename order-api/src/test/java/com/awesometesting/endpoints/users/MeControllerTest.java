package com.awesometesting.endpoints.users;

import com.awesometesting.DomainHelper;
import com.awesometesting.dto.auth.SignUpRequest;
import com.awesometesting.dto.user.UserDto;
import com.awesometesting.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class MeControllerTest extends DomainHelper {

    private static final String ME_ENDPOINT = "/api/users/me";

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldReturnMyDataAsUser() {
        // given
        String apiToken = loginAsAdminAndGetToken();

        // when
        ResponseEntity<UserDto> response =
                executeGet(ME_ENDPOINT, getHeadersWith(apiToken), UserDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().username()).isEqualTo(ADMIN);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldReturnMyDataAsAdmin() {
        // given
        SignUpRequest randomUser = UserUtil.getRandomUser();
        String apiToken = registerAndGetToken(randomUser);

        // when
        ResponseEntity<UserDto> response =
                executeGet(ME_ENDPOINT, getHeadersWith(apiToken), UserDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().username()).isEqualTo(randomUser.getUsername());
    }

    @Test
    public void shouldGet401AsUnauthorized() {
        // when
        ResponseEntity<?> response = executeGet(ME_ENDPOINT, getJsonOnlyHeaders());

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}

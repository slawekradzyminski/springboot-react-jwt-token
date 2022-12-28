package com.awesometesting.endpoints.openpublic;

import com.awesometesting.DomainHelper;
import com.awesometesting.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ConstantConditions")
public class GetUsersPublicControllerTest extends DomainHelper {

    private static final String NUMBER_OF_USERS_ENDPOINT = "/public/numberOfUsers";

    @Test
    public void shouldGetNumberOfUsersAsAdmin() {
        // given
        String adminToken = loginAsAdminAndGetToken();

        // when
        ResponseEntity<Integer> response =
                executeGet(NUMBER_OF_USERS_ENDPOINT,
                        getHeadersWith(adminToken),
                        Integer.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().intValue()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void shouldGetNumberOfUsersAsClient() {
        // given
        String clientToken = registerAndGetToken(UserUtil.getRandomUser());

        // when
        // when
        ResponseEntity<Integer> response =
                executeGet(NUMBER_OF_USERS_ENDPOINT,
                        getHeadersWith(clientToken),
                        Integer.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().intValue()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void shouldGetNumberOfUsersAsNotLoggedIn() {
        // when
        ResponseEntity<Integer> response =
                executeGet(NUMBER_OF_USERS_ENDPOINT,
                        getJsonOnlyHeaders(),
                        Integer.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().intValue()).isGreaterThanOrEqualTo(1);
    }

}

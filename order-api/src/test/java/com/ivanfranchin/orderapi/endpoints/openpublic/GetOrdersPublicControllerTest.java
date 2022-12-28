package com.ivanfranchin.orderapi.endpoints.openpublic;

import com.ivanfranchin.orderapi.DomainHelper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.ivanfranchin.orderapi.util.UserUtil.getRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ConstantConditions")
public class GetOrdersPublicControllerTest extends DomainHelper {

    private static final String NUMBER_OF_ORDERS_ENDPOINT = "/public/numberOfOrders";

    @Test
    public void shouldGetNumberOfOrdersAsAdmin() {
        // given
        String adminToken = loginAsAdminAndGetToken();

        // when
        ResponseEntity<Integer> response =
                executeGet(NUMBER_OF_ORDERS_ENDPOINT,
                        getHeadersWith(adminToken),
                        Integer.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().intValue()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void shouldGetNumberOfOrdersAsClient() {
        // given
        String clientToken = registerAndGetToken(getRandomUser());

        // when
        ResponseEntity<Integer> response =
                executeGet(NUMBER_OF_ORDERS_ENDPOINT,
                        getHeadersWith(clientToken),
                        Integer.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().intValue()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void shouldGetNumberOfOrdersAsNotLoggedIn() {
        // when
        ResponseEntity<Integer> response =
                executeGet(NUMBER_OF_ORDERS_ENDPOINT,
                        getJsonOnlyHeaders(),
                        Integer.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().intValue()).isGreaterThanOrEqualTo(0);
    }

}

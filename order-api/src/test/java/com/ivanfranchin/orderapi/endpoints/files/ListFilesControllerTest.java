package com.ivanfranchin.orderapi.endpoints.files;

import com.ivanfranchin.orderapi.dto.files.ResponseFile;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.ivanfranchin.orderapi.util.UserUtil.getRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ConstantConditions")
public class ListFilesControllerTest extends AbstractFileTest {

    private static final String FILES_ENDPOINT = "/files";

    @Test
    public void shouldSucceedAsAdmin() {
        // given
        String token = loginAsAdminAndGetToken();
        uploadValidFile(token);
        uploadValidFile(token);

        // when
        ResponseEntity<ResponseFile[]> response = executeGet(FILES_ENDPOINT, getHeadersWith(token), ResponseFile[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    public void shouldSucceedAsUser() {
        // given
        String token = registerAndGetToken(getRandomUser());
        uploadValidFile(token);
        uploadValidFile(token);

        // when
        ResponseEntity<ResponseFile[]> response = executeGet(FILES_ENDPOINT, getHeadersWith(token), ResponseFile[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    public void shouldReturn401AsUnauthorized() {
        // given
        ResponseEntity<?> response = executeGet(FILES_ENDPOINT, new HttpHeaders());

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}

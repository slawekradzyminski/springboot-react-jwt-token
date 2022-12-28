package com.awesometesting.endpoints.files;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

import static com.awesometesting.util.UserUtil.getRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ConstantConditions")
public class UploadFileControllerTest extends AbstractFileTest {

    @Test
    public void shouldSucceedAsAdmin() {
        // given
        String token = loginAsAdminAndGetToken();

        // when
        ResponseEntity<?> response = uploadValidFile(token);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldSucceedAsUser() {
        // given
        String token = registerAndGetToken(getRandomUser());

        // when
        ResponseEntity<?> response = uploadValidFile(token);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldFailOnTooBigFile() {
        // given
        String token = loginAsAdminAndGetToken();

        // when
        ResponseEntity<?> response = uploadTooBigFile(token);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.EXPECTATION_FAILED);
        assertThat(response.getBody().toString()).contains("File too large");
    }

    @Test
    public void shouldReturn401AsUnauthorized() {
        // given
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(getBody(1), getMultipartFormDataHeaders());

        // when
        ResponseEntity<?> response = restTemplate.postForEntity(UPLOAD_ENDPOINT, requestEntity, Object.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private HttpHeaders getMultipartFormDataHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

}

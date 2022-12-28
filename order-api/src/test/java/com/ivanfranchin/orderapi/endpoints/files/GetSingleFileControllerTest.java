package com.ivanfranchin.orderapi.endpoints.files;

import com.ivanfranchin.orderapi.dto.files.ResponseFile;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ConstantConditions")
public class GetSingleFileControllerTest extends AbstractFileTest {

    private static final String FILES_ENDPOINT = "/files";

    private static final RestTemplate BLANK_REST_TEMPLATE = new RestTemplate();

    @Test
    public void shouldSucceedWithoutAuthorization() {
        // given
        String token = loginAsAdminAndGetToken();
        uploadValidFile(token);
        ResponseFile[] allFiles = executeGet(FILES_ENDPOINT, getHeadersWith(token), ResponseFile[].class).getBody();
        String fileUrl = allFiles[0].getUrl();

        // when
        byte[] imageBytes = BLANK_REST_TEMPLATE.getForObject(fileUrl, byte[].class);

        // then
        assertThat(imageBytes).hasSize(1);
    }

    @Test
    public void shouldGet404ForNotExistingFile() {
        // when
        ResponseEntity<?> response = executeGet("/files/" + UUID.randomUUID(), new HttpHeaders(), Object.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}

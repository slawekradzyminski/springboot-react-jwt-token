package com.awesometesting.endpoints.files;

import com.awesometesting.DomainHelper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

public class AbstractFileTest extends DomainHelper {

    static final String UPLOAD_ENDPOINT = "/files/upload";

    @SneakyThrows
    static MultiValueMap<String, Object> getBody(int fileSize) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getTestFile(fileSize));
        return body;
    }

    protected static Resource getTestFile(int fileSize) throws IOException {
        Path testFile = Files.createTempFile(RandomStringUtils.randomAlphabetic(10), ".txt");
        Files.write(testFile, new byte[fileSize]);
        return new FileSystemResource(testFile.toFile());
    }

    ResponseEntity<?> uploadValidFile(String token) {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(getBody(1), getAllRequiredHeaders(token));
        return restTemplate.postForEntity(UPLOAD_ENDPOINT, requestEntity, Object.class);
    }

    ResponseEntity<?> uploadTooBigFile(String token) {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(getBody(2200000), getAllRequiredHeaders(token));
        return restTemplate.postForEntity(UPLOAD_ENDPOINT, requestEntity, Object.class);
    }

    private HttpHeaders getAllRequiredHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add(HttpHeaders.AUTHORIZATION, MessageFormat.format("Bearer {0}", token));
        return headers;
    }

}

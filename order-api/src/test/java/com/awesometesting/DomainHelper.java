package com.awesometesting;

import com.awesometesting.dto.auth.AuthResponse;
import com.awesometesting.dto.auth.LoginRequest;
import com.awesometesting.dto.auth.SignUpRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;

public abstract class DomainHelper extends HttpHelper {

    protected static final String LOGIN_ENDPOINT = "/auth/authenticate";
    protected static final String REGISTER_ENDPOINT = "/auth/signup";
    protected static final String USERS_ENDPOINT = "/api/users";
    protected static final String ADMIN = "admin";

    protected <T> ResponseEntity<T> attemptLogin(LoginRequest loginDetails, Class<T> clazz) {
        return executePost(
                LOGIN_ENDPOINT,
                loginDetails,
                getJsonOnlyHeaders(),
                clazz);
    }

    @SuppressWarnings("ConstantConditions")
    protected String registerAndGetToken(SignUpRequest signUpRequest) {
        return executePost(
                REGISTER_ENDPOINT,
                signUpRequest,
                getJsonOnlyHeaders(),
                AuthResponse.class)
                .getBody()
                .accessToken();
    }

    @SuppressWarnings("ConstantConditions")
    protected void registerUser(SignUpRequest signUpRequest) {
        executePost(
                REGISTER_ENDPOINT,
                signUpRequest,
                getJsonOnlyHeaders(),
                AuthResponse.class);
    }

    @SuppressWarnings("ConstantConditions")
    protected String loginAsAdminAndGetToken() {
        return executePost(
                LOGIN_ENDPOINT,
                new LoginRequest(ADMIN, ADMIN),
                getJsonOnlyHeaders(),
                AuthResponse.class)
                .getBody()
                .accessToken();
    }

    protected String getUserEndpoint(String username) {
        return MessageFormat.format("/api/users/{0}", username);
    }

    protected HttpHeaders getHeadersWith(String token) {
        HttpHeaders headers = getJsonOnlyHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, MessageFormat.format("Bearer {0}", token));
        return headers;
    }

}

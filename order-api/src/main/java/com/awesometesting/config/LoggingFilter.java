package com.awesometesting.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    private static final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private static boolean isFileDownloadRequest(HttpServletRequest request) {
        String[] lastPartOfUrl = request.getRequestURI().split("/");
        return lastPartOfUrl.length > 2 && UUID_REGEX.matcher(lastPartOfUrl[2]).matches();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;

        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());
        String responseBody = filteredResponseBody(request, response, responseWrapper);

        if (!swaggerRequest(request.getRequestURI())) {
            log.info(
                    "FINISHED PROCESSING : METHOD={}; URI={}; REQUEST PAYLOAD={}; RESPONSE CODE={}; RESPONSE={}; TIME TAKEN={}",
                    request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody,
                    timeTaken);
        }
        responseWrapper.copyBodyToResponse();
    }

    private boolean swaggerRequest(String requestURI) {
        return requestURI.contains("swagger") || requestURI.contains("v3/api-docs");
    }

    private String filteredResponseBody(HttpServletRequest request, HttpServletResponse response, ContentCachingResponseWrapper responseWrapper) {
        if (isFileDownloadRequest(request)) {
            return "CUSTOM OBFUSCATED FILE BODY TO PREVENT BLOB LOGGING";
        }

        return getStringValue(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
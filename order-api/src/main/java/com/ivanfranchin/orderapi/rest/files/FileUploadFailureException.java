package com.ivanfranchin.orderapi.rest.files;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class FileUploadFailureException extends RuntimeException {

    public FileUploadFailureException(String message) {
        super(message);
    }
}

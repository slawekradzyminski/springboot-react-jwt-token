package com.awesometesting.rest.files;

import com.awesometesting.dto.files.FilesResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<FilesResponseMessage> handleMaxSizeException() {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FilesResponseMessage("File too large!"));
    }
}
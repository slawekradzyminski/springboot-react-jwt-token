package com.ivanfranchin.orderapi.rest;

import com.ivanfranchin.orderapi.rest.dto.ResponseMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileGetExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Entity not found!"));
    }
}
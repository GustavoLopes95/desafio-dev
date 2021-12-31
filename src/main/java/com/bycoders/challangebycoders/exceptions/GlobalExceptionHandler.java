package com.bycoders.challangebycoders.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<StandardError> handleDomainException(DomainException e) {
        return ResponseEntity.unprocessableEntity().body(new StandardError(e.getMessage()));
    }
}

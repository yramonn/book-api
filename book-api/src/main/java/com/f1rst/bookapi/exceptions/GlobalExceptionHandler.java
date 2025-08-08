package com.f1rst.bookapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(OpenLibraryClientException.class)
    public ResponseEntity<?> handleOpenLibraryClientException(OpenLibraryClientException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error to call OpenLibraryAPI", ex.getMessage()));
    }

    static class ErrorResponse {
        public String error;
        public String details;

        public ErrorResponse(String error, String details) {
            this.error = error;
            this.details = details;
        }
    }
}

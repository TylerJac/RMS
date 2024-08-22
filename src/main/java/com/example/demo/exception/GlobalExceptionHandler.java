package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
// TABLE SERVICE HANDLER EXCEPTION NEEDED FOR TABLE ASSIGNING
    @ExceptionHandler(TableNotFoundException.class)
    public ResponseEntity<String> handleTableNotFoundException(TableNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TableAlreadyOccupiedException.class)
    public ResponseEntity<String> handleTableAlreadyOccupiedException(TableAlreadyOccupiedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // You can add more exception handlers here as needed
    public static class TableNotFoundException extends RuntimeException {
        public TableNotFoundException(String message) {
            super(message);
        }
    }

    public static class TableAlreadyOccupiedException extends RuntimeException {
        public TableAlreadyOccupiedException(String message) {
            super(message);
        }
    }

    // THE END OF SIMONS NEEDED EXCEPTION HANDLER FOR TABLE SERVICE
}

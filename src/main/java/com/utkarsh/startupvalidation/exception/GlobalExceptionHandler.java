package com.utkarsh.startupvalidation.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
    handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> error =
                new HashMap<>();

        FieldError fieldError =
                ex.getBindingResult()
                        .getFieldError();

        error.put(
                "message",
                fieldError != null
                        ? fieldError.getDefaultMessage()
                        : "Validation Failed"
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>>
    handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {

        Map<String, String> error = new HashMap<>();
        error.put(
                "message",
                "Method " + ex.getMethod()
                        + " not allowed. Use "
                        + String.join(", ", ex.getSupportedMethods())
                        + " instead.");

        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>>
    handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", "Email already registered");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>>
    handleRuntimeException(
            RuntimeException ex) {

        Map<String, String> error =
                new HashMap<>();

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>>
    handleException(
            Exception ex) {

        Map<String, String> error =
                new HashMap<>();

        error.put(
                "message",
                "Something Went Wrong"
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
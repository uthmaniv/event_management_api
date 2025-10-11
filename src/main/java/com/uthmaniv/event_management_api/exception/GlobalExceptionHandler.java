package com.uthmaniv.event_management_api.exception;

import com.uthmaniv.event_management_api.util.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ApiError handleEventAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public  ApiError handleEventNotFoundException(ResourceNotFoundException ex) {
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ApiError handleInvalidFileFormatException(InvalidFileFormatException ex) {
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleIOException(IOException ex) {
        return new ApiError("Error processing file: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        body.put("status", "error");
        body.put("message", "Validation failed");
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

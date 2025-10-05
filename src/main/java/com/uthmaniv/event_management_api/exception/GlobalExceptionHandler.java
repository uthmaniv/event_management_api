package com.uthmaniv.event_management_api.exception;

import com.uthmaniv.event_management_api.util.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventAlreadyExistsException.class)
    public ApiError handleEventAlreadyExistsException(EventAlreadyExistsException ex) {
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public  ApiError handleParticipantNotFoundException(ParticipantNotFoundException ex) {
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler(EventNotFoundException.class)
    public  ApiError handleEventNotFoundException(EventNotFoundException ex) {
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
}

package com.uthmaniv.event_management_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileFormatException extends RuntimeException{
    public InvalidFileFormatException(String message) {
        super(message);
    }
}

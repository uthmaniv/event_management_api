package com.uthmaniv.event_management_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EventAlreadyExistsException extends RuntimeException{

    public EventAlreadyExistsException(String message) {
        super(message);
    }
}

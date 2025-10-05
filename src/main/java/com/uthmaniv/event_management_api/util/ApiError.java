package com.uthmaniv.event_management_api.util;

import java.time.LocalDateTime;

public record ApiError(String message, LocalDateTime timestamp){

    public ApiError(String message){
        this(message,LocalDateTime.now());
    }
}

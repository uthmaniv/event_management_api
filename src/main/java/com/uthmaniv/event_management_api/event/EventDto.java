package com.uthmaniv.event_management_api.event;

import java.time.LocalDateTime;

public record EventDto(
        String title,
        String description,
        String location,
        LocalDateTime dateTime
) {}

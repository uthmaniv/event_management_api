package com.uthmaniv.event_management_api.participant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParticipantDto (
        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Phone number is required")
        Long phoneNumber,

        @NotNull(message = "Event ID is required")
        long eventId
){}

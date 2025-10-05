package com.uthmaniv.event_management_api.participant;

public record ParticipantDto (
        String firstName,
        String lastName,
        String email,
        Long phoneNumber
){}

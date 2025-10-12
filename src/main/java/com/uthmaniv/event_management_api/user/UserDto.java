package com.uthmaniv.event_management_api.user;


import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Password is required")
        String password){}

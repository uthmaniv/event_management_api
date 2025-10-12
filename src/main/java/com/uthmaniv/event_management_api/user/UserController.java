package com.uthmaniv.event_management_api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody UserDto userDto) {
        userService.register(userDto);
    }

    @GetMapping("/demo")
    @ResponseStatus(HttpStatus.OK)
    public String hello(){
        return "HELLO";
    }
}

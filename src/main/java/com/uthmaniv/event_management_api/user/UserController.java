package com.uthmaniv.event_management_api.user;

import com.uthmaniv.event_management_api.util.ApiSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getAllUsers() {
        return new ApiSuccess("success", userService.getAllUsers());
    }

    @GetMapping("/debug")
    public ResponseEntity<?> debugAuth(Authentication authentication) {
        return ResponseEntity.ok(authentication.getAuthorities());
    }


}

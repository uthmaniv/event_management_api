package com.uthmaniv.event_management_api.user;

import com.uthmaniv.event_management_api.exception.ResourceAlreadyExistsException;
import com.uthmaniv.event_management_api.exception.ResourceNotFoundException;
import com.uthmaniv.event_management_api.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(UserDto dto) {
        if (userRepository.existsByUsername(dto.username())){
            throw new ResourceAlreadyExistsException("User Exists");
        }
        User user = new User(dto.username(), dto.password());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User Not found"));
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            UserResponseDto userDto = new UserResponseDto(user.getUsername(),user.getEvents().size());
            userResponseDtos.add(userDto);
        }
        return userResponseDtos;
    }

    public String verifyUser(UserDto userDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userDto.username(),
                        userDto.password())
                );
        if (authentication.isAuthenticated())
            return jwtService.generateToken(userDto);
        return "Authentication failed";
    }
}

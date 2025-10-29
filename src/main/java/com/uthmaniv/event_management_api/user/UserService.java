package com.uthmaniv.event_management_api.user;

import com.uthmaniv.event_management_api.exception.ResourceAlreadyExistsException;
import com.uthmaniv.event_management_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public void register(UserDto dto) {
        if (userRepository.existsByUsername(dto.username())){
            throw new ResourceAlreadyExistsException("User Exits");
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
}

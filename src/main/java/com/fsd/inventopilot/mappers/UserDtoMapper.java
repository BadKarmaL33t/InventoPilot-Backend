package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.RegisterRequest;
import com.fsd.inventopilot.dtos.UserDto;
import com.fsd.inventopilot.models.Role;
import com.fsd.inventopilot.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    private final PasswordEncoder encoder;

    public UserDtoMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public UserDto mapToDto(User user) {
        UserDto dto = new UserDto();

        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());

        return dto;
    }

    public User mapToUser(RegisterRequest registerRequest) {
        User user = new User();

        user.setUsername(registerRequest.getUsername());
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);

        return user;
    }
}

package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.RegisterRequest;
import com.fsd.inventopilot.dtos.UserDto;
import com.fsd.inventopilot.models.Role;
import com.fsd.inventopilot.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

@Component
public class UserDtoMapper {
    private final PasswordEncoder encoder;

    public UserDtoMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public UserDto mapToDto(User user) {
        UserDto dto = new UserDto();

        BeanUtils.copyProperties(user, dto);

        return dto;
    }

    public User mapToUser(RegisterRequest registerRequest) {
        User user = new User();

        BeanUtils.copyProperties(registerRequest, user);
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);

        return user;
    }
}

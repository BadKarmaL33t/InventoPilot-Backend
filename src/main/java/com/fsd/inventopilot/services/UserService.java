package com.fsd.inventopilot.services;

import com.fsd.inventopilot.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserDetailsByUsername(String username);
    UserDto updateUserDetails(String username, UserDto newUser);
    void deleteUser(String username);
}

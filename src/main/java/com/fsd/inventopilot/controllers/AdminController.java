package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.UserDto;
import com.fsd.inventopilot.models.User;
import com.fsd.inventopilot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/secure/admin/users")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos;
        userDtos = userService.getAllUsers();

        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("username") String username) {
        UserDto dto = userService.getUserDetailsByUsername(username);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{username}")
    public ResponseEntity<UserDto> updateUserDetails(@PathVariable String username,@Valid @RequestBody UserDto updatedUser) {
        UserDto dto = userService.updateUserDetails(username, updatedUser);

        return ResponseEntity.ok().body(dto);
    }
}

package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.UserDto;
import com.fsd.inventopilot.models.User;
import com.fsd.inventopilot.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/app/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<UserDto> updateUserDetails(@PathVariable String username, @RequestBody UserDto updatedUser) {
        UserDto dto = userService.updateUserDetails(username, updatedUser);

        return ResponseEntity.ok().body(dto);
    }
}

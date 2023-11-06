package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.UserDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.exceptions.UnauthorizedAccessException;
import com.fsd.inventopilot.mappers.UserDtoMapper;
import com.fsd.inventopilot.models.User;
import com.fsd.inventopilot.repositories.UserRepository;
import com.fsd.inventopilot.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.encoder = encoder;
    }

    private boolean hasAdminAuthority(org.springframework.security.core.userdetails.User user) {
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto dto = userDtoMapper.mapToDto(user);
            userDtos.add(dto);
        }
        return userDtos;
    }

    public UserDto getUserDtoByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            com.fsd.inventopilot.models.User foundUser = user.get();

            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            // Verify that the authenticated user is the author of the existing post
            if (foundUser.getUsername().equals(authUser.getUsername()) || hasAdminAuthority(authUser)) {
                return userDtoMapper.mapToDto(foundUser);
            } else {
                throw new UnauthorizedAccessException("You do not have permission to view these user details");
            }
        } else {
            throw new RecordNotFoundException("User with username: " + username + " was not found");
        }
    }

    public UserDto updateUserDetails(String username, UserDto updatedUser) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            com.fsd.inventopilot.models.User thisUser = user.get();

            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            // Verify that the authenticated user is the author of the existing post
            if (!thisUser.getUsername().equals(authUser.getUsername()) || hasAdminAuthority(authUser)) {
                throw new UnauthorizedAccessException("You do not have permission to update this user");
            }

            // Update the user with the new data
            if (updatedUser.getUsername() != null) {
                thisUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getFirstname() != null) {
                thisUser.setFirstname(updatedUser.getFirstname());
            }
            if (updatedUser.getLastname() != null) {
                thisUser.setLastname(updatedUser.getLastname());
            }
            if (updatedUser.getEmail() != null) {
                thisUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null) {
                thisUser.setPassword(encoder.encode(updatedUser.getPassword()));
            }

            User saveUser = userRepository.save(thisUser);

            return userDtoMapper.mapToDto(saveUser);

        } else {
            throw new RecordNotFoundException("User with username: " + username + " was not found");
        }
    }

    public void deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            com.fsd.inventopilot.models.User thisUser = user.get();

            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            // Verify that the authenticated user is the author of the existing post
            if (thisUser.getUsername().equals(authUser.getUsername()) || hasAdminAuthority(authUser)) {
                userRepository.deleteById(username);
            } else {
                throw new UnauthorizedAccessException("You do not have permission to delete this user");
            }
        } else {
            throw new RecordNotFoundException("User with username: " + username + " was not found");
        }
    }
}

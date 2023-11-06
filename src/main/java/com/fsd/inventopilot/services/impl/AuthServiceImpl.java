package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.JwtAuthResponse;
import com.fsd.inventopilot.dtos.RegisterRequest;
import com.fsd.inventopilot.dtos.SignInRequest;
import com.fsd.inventopilot.mappers.UserDtoMapper;
import com.fsd.inventopilot.models.User;
import com.fsd.inventopilot.repositories.UserRepository;
import com.fsd.inventopilot.services.AuthService;
import com.fsd.inventopilot.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDtoMapper userDtoMapper;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authManager, JwtService jwtService, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userDtoMapper = userDtoMapper;
    }

    public User register(RegisterRequest registerRequest) {
        // Check if a user with the same username already exists
        Optional<User> existingUserByUsername = userRepository.findByUsername(registerRequest.getUsername());
        if (existingUserByUsername.isPresent()) {
            throw new RuntimeException("User with this username already exists");
        }

        // Check if a user with the same email already exists
        Optional<User> existingUserByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        // Create a new user
        User user = userDtoMapper.mapToUser(registerRequest);

        return userRepository.save(user);
    }

    public JwtAuthResponse signIn(SignInRequest signInRequest) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()));

        var user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        var jwt = jwtService.generateJwt(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setJwt(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);

        return jwtAuthResponse;
    }

    public JwtAuthResponse refreshTokenRequest(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow();

        if (jwtService.isTokenValid(refreshToken, user)) {
            var jwt = jwtService.generateJwt(user);

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setJwt(jwt);
            jwtAuthResponse.setRefreshToken(refreshToken);

            return jwtAuthResponse;
        }
        return null;
    }

    public JwtAuthResponse dummyRefreshTokenRequest(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow();

        if (jwtService.isTokenValid(refreshToken, user)) {
            var jwt = jwtService.generateJwt(user);

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setJwt(jwt);
            jwtAuthResponse.setRefreshToken(refreshToken);

            return jwtAuthResponse;
        }
        return null;
    }
}

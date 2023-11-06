package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.models.User;
import com.fsd.inventopilot.repositories.UserRepository;
import com.fsd.inventopilot.services.CustomUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException("User with username: " + username + " was not found");
        }
        return userOptional;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user information from your UserService or UserRepository
        com.fsd.inventopilot.models.User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Create a UserDetails object using the fetched user data and roles
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // Password should be hashed
                authorities // Set user role(s) as GrantedAuthorities
        );
    }
}

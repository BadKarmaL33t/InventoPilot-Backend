package com.fsd.inventopilot.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String extractUsername(String jwt);
    String generateJwt(UserDetails userDetails);
    String generateRefreshToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    );
    boolean isTokenValid(String jwt, UserDetails userDetails);
}

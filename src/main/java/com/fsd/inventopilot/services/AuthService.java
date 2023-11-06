package com.fsd.inventopilot.services;

import com.fsd.inventopilot.dtos.JwtAuthResponse;
import com.fsd.inventopilot.dtos.RegisterRequest;
import com.fsd.inventopilot.dtos.SignInRequest;
import com.fsd.inventopilot.models.User;

public interface AuthService {

    User register(RegisterRequest registerRequest);
    JwtAuthResponse signIn(SignInRequest signInRequest);
    JwtAuthResponse refreshTokenRequest(String refreshTokenRequest);

    // dummy refreshtoken om in postman te laten zien dat deze werkt
    JwtAuthResponse dummyRefreshTokenRequest(String refreshToken);
}

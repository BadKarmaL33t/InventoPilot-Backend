package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.JwtAuthResponse;
import com.fsd.inventopilot.dtos.RegisterRequest;
import com.fsd.inventopilot.dtos.SignInRequest;
import com.fsd.inventopilot.models.User;
import com.fsd.inventopilot.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/secure/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signIn(@Valid @RequestBody SignInRequest signInRequest, @NotNull HttpServletResponse response) {
        JwtAuthResponse jwtAuthResponse = authService.signIn(signInRequest);

        // Set an HTTP-only cookie for the refresh token
        String refreshToken = jwtAuthResponse.getRefreshToken();

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setPath("/"); // You can adjust the path as needed
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // Always set 'secure' when using SameSite=None
        refreshTokenCookie.setAttribute("SameSite", "Lax");

        response.addCookie(refreshTokenCookie);

        // Return the JWT and other response data
        // Om met postman te werken is het nu zo geconfigureerd dat de refreshtoken ook wordt meegestuurd met de response,
        // maar in de praktijk zou ik deze nooit meesturen met de response.
        // De refresh token wordt hierboven in een HTTP-only cookie gestopt, zodat de frontend hiervan gebruik kan maken
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refresh(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String refreshedToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshedToken = cookie.getValue();
                    break;
                }
            }
        }

        return ResponseEntity.ok(authService.refreshTokenRequest(refreshedToken));
    }

    @PostMapping("/refresh-dummy")
    public ResponseEntity<JwtAuthResponse> refresh(@RequestBody String refreshToken) {
       return ResponseEntity.ok(authService.dummyRefreshTokenRequest(refreshToken));
    }
}

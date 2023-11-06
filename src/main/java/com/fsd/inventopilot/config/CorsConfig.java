package com.fsd.inventopilot.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**") // Apply CORS to all endpoints
                        .allowedOrigins("http://localhost:5173") // Whitelist your frontend origin
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders(HttpHeaders.AUTHORIZATION,
                                HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
                        .allowCredentials(true)
                        .maxAge(3600L);
            }
        };
    }
}
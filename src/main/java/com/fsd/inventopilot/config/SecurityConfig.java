package com.fsd.inventopilot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/secure/auth/**").permitAll()
                        .requestMatchers("/secure/admin/users", "/secure/admin/users/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.OPTIONS, "/app/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/app/users/**"
                        ).authenticated()
                        .requestMatchers(HttpMethod.GET,
                                "/app/locations",
                                "/app/locations/**",
                                "/app/orders",
                                "/app/orders/**",
                                "/app/components",
                                "/app/components/**",
                                "/app/products",
                                "/app/products/**",
                                "/app/raws",
                                "/app/raws/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.PATCH,
                                "/{id}/status"
                        ).permitAll()
                        .requestMatchers(
                                "/app/users",
                                "/app/users/**",
                                "/app/locations",
                                "/app/locations/**",
                                "/app/orders",
                                "/app/orders/**",
                                "/app/components",
                                "/app/components/**",
                                "/app/products",
                                "/app/products/**",
                                "/app/raws",
                                "/app/raws/**"
                        ).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/app/locations",
                                "/app/locations/**",
                                "/app/orders",
                                "/app/orders/**",
                                "/app/components",
                                "/app/components/**",
                                "/app/products",
                                "/app/products/**",
                                "/app/raws",
                                "/app/raws/**"
                        ).hasAuthority("ROLE_SUPERUSER")
                        .requestMatchers(HttpMethod.PATCH,
                                "/app/locations",
                                "/app/locations/**",
                                "/app/orders",
                                "/app/orders/**",
                                "/app/components",
                                "/app/components/**",
                                "/app/products",
                                "/app/products/**",
                                "/app/raws",
                                "/app/raws/**"
                        ).hasAuthority("ROLE_SUPERUSER")
                        .anyRequest().denyAll()
                )
                .csrf(AbstractHttpConfigurer::disable) // .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

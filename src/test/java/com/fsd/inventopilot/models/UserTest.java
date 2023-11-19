package com.fsd.inventopilot.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        // Create a User object for testing
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("testPassword");
        user.setRole(Role.USER);

        // Validate the user properties
        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("testPassword", user.getPassword());
        assertEquals(Role.USER, user.getRole());
    }
}
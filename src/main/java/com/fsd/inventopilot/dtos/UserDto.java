package com.fsd.inventopilot.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.fsd.inventopilot.services.AllowedValue;
import lombok.Data;

@Data
public class UserDto {
    @Pattern(regexp = "^[a-zA-Z]*[a-zA-Z0-9-_]{3,23}$")
    private String username;
    @Pattern(regexp = "^[a-zA-Z-]+$")
    @Size(min = 2, max = 25)
    private String firstname;
    @Pattern(regexp = "^[a-zA-Z-]+$")
    @Size(min = 2, max = 50)
    private String lastname;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9';<>&|/\\\\]).{8,24}$")
    private String password;
    @AllowedValue
    private String role;
}

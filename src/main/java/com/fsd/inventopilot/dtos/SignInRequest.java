package com.fsd.inventopilot.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignInRequest {
    @Pattern(regexp = "^[a-zA-Z]*[a-zA-Z0-9-_]{3,23}$")
    private String username;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9';<>&|/\\\\]).{8,24}$")
    private String password;
}

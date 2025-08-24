package com.Spectro_Systems.Student.Management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTo {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}

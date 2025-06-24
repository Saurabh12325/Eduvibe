package com.example.demo.DTO;

import com.example.demo.Entity.Role;
import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String mobileNumber;
    private String email;
    private String otp;
    private Role role;
}

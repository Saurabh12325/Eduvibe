package com.example.demo.DTO;

import com.example.demo.Entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String mobileNumber;
    private String email;
    private String otp;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

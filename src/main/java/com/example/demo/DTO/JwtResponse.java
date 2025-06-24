package com.example.demo.DTO;

import lombok.Data;

@Data
public class JwtResponse {
    private String message;
    private String token;
    private String email;
}

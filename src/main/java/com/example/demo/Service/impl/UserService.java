package com.example.demo.Service.impl;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<Object> signUp(SignUpRequest signUpRequest);
    public ResponseEntity<Object> login(LoginRequest loginRequest);
}

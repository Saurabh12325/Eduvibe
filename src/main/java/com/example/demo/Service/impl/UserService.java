package com.example.demo.Service.impl;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

      ResponseEntity<?> login(LoginRequest loginRequest);
    String signUp(SignUpRequest signUpRequest);

}

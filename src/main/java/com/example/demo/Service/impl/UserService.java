package com.example.demo.Service.impl;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public String login(LoginRequest loginRequest);
    public String signUp(SignUpRequest signUpRequest);

}

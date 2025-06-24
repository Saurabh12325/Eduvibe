package com.example.demo.Mapper;

import com.example.demo.DTO.SignUpRequest;
import com.example.demo.Entity.user;

public class mappper {

    public SignUpRequest mapToDto(user user , SignUpRequest signUpRequest) {

        signUpRequest.setUsername(user.getUsername());
        signUpRequest.setPassword(user.getPassword());
        signUpRequest.setEmail(user.getEmail());
        signUpRequest.setRole(user.getRole());

        return signUpRequest;
    }
}

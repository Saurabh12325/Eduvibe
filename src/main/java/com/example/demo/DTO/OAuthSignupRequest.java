package com.example.demo.DTO;

import com.example.demo.Entity.Provider;
import lombok.Data;

@Data
public class OAuthSignupRequest {
    private String email;
    private String username;
    private Provider provider; // GOOGLE or GITHUB

}

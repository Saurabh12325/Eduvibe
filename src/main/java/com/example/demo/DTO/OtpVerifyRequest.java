package com.example.demo.DTO;

import lombok.Data;

@Data
public class OtpVerifyRequest {
        private String email;
        private String otp;

}

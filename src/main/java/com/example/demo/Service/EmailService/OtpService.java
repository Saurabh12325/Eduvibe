package com.example.demo.Service.EmailService;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(10000));
    }

}

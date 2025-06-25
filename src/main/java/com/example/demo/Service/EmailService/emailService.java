package com.example.demo.Service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class emailService {
      @Autowired
      private JavaMailSender mailSender;

    public void sendOtpEmail(String Email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(Email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }
}

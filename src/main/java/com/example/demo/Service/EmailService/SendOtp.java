package com.example.demo.Service.EmailService;

import com.example.demo.Entity.Provider;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class SendOtp {
    @Autowired
    private emailService emailService;

    @Autowired
    private UserRepository userRepository;


    public String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
    public String sendOtpToEmail(String email) {

        String otp = generateOtp();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.orElseGet(User::new);
        user.setEmail(email);
        user.setOtp(otp);
        user.setOtpGeneratedAt(LocalDateTime.now());
        user.setProvider(Provider.Local);

        userRepository.save(user);

        emailService.sendOtpEmail(email, otp);
        return "OTP sent to your email!";
    }



}

package com.example.demo.Service;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.SignUpRequest;
import com.example.demo.Entity.Provider;
import com.example.demo.Entity.User;
import com.example.demo.JWT.JwtUtils;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.EmailService.EmailService;
import com.example.demo.Service.EmailService.SendOtp;
import com.example.demo.Service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService implements UserService {

    private final UserRepository userRepository;
    private final SendOtp sendOtp;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;



    @Override
    public String login(LoginRequest loginRequest) {
      return null;
    }
    @Override
    public String signUp(SignUpRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        String otp = sendOtp.generateOtp();
        if (optionalUser.isEmpty()) {
            User user = optionalUser.orElseGet(User::new);

            user.setOtp(otp);
            user.setEmail(request.getEmail());
            user.setOtpGeneratedAt(LocalDateTime.now());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setMobileNumber(request.getMobileNumber());
            user.setRole(request.getRole());
            user.setProvider(Provider.Local);
            user.setCreatedAt(LocalDateTime.now());

            userRepository.save(user);
            emailService.sendOtpEmail(request.getEmail(),otp);
            String token = jwtUtils.generateToken(user.getEmail());
            return "OTP sent to your email. Please verify to complete signup. " + token;


        }

        return "User Already register Log in ";

    }

    public User verifyOtp(String email, String requestOtp) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User with this email does not exist.");
        }

        User user = optionalUser.get();

        if (user.isEmailVerified()) {
            throw new RuntimeException("Email already verified.");
        }

        if (!requestOtp.equals(user.getOtp())) {
            throw new RuntimeException("Invalid OTP.");
        }

        if (user.getOtpGeneratedAt() == null ||
                Duration.between(user.getOtpGeneratedAt(), LocalDateTime.now()).toMinutes() > 5) {
            throw new RuntimeException("OTP expired. Please request a new one.");
        }

        user.setEmailVerified(true);
       return userRepository.save(user);


    }


}



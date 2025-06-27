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
import org.springframework.http.HttpStatus;
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

            return "OTP sent to your email. Please verify to complete signup. ";


        }
        else if (!optionalUser.get().isEmailVerified()) {
            return "Please verify your email with otp";
        }

        return "User Already register Please login";

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
    public String resendOtp(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not registered.");
        }

        User user = optionalUser.get();

        if (user.isEmailVerified()) {
            return "Email already verified.";
        }
        if (user.getOtpGeneratedAt() != null &&
                Duration.between(user.getOtpGeneratedAt(), LocalDateTime.now()).toMinutes() < 5) {
            long remainingTime = 5 - Duration.between(user.getOtpGeneratedAt(), LocalDateTime.now()).toMinutes();
            return "Please wait " + remainingTime + " more minute(s) before resending OTP.";
        }
        String newOtp = sendOtp.generateOtp();
        user.setOtp(newOtp);
        user.setOtpGeneratedAt(LocalDateTime.now());
        userRepository.save(user);
        emailService.sendOtpEmail(email, newOtp);
        return "New OTP sent to your email.";
    }


    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found with this email"));
        if (user.isEmailVerified() && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String token = jwtUtils.generateToken(user.getEmail());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        }
    }

}



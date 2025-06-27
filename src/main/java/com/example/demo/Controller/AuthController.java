package com.example.demo.Controller;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.OtpVerifyRequest;
import com.example.demo.DTO.SignUpRequest;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final StudentService studentService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(studentService.signUp(request));
    }
    @PatchMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerifyRequest request) {
        studentService.verifyOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok("OTP verified successfully.");
    }
    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestParam String email) {
        return ResponseEntity.ok(studentService.resendOtp(email));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout() {
      userRepository.deleteAll();
      return new ResponseEntity<>("Logout successful", HttpStatus.OK);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
       return  ResponseEntity.ok(studentService.login(loginRequest)) ;
    }


}

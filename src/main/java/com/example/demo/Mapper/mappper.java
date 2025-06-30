//package com.example.demo.Mapper;
//
//
//import com.example.demo.DTO.OAuthSignupRequest;
//import com.example.demo.DTO.SignUpRequest;
//import com.example.demo.Entity.Provider;
//import com.example.demo.Entity.Role;
//import com.example.demo.Entity.User;
//
//import java.time.LocalDateTime;
//
//public class mappper {
//
//        // Local Signup (email + password + OTP)
//        public static User toUserFromSignup(SignUpRequest dto) {
//            User user = new User();
//            user.setUsername(dto.getUsername());
//            user.setEmail(dto.getEmail());
//            user.setPassword(dto.getPassword()); // In production, encode it
//            user.setMobileNumber(dto.getMobileNumber());
//            user.setProvider(Provider.Local);
//            user.setRole(Role.STUDENT);
//            user.setCreatedAt(LocalDateTime.now());
//            user.setEmailVerified(true); // After OTP success
//            return user;
//        }
//
//        // OAuth Signup (e.g. Google)
//        public static User toUserFromOAuth(OAuthSignupRequest dto) {
//            User user = new User();
//            user.setEmail(dto.getEmail());
//            user.setUsername(dto.getUsername());
//            user.setProvider(Provider.Google);
//            user.setRole(Role.TEACHER);
//            user.setCreatedAt(LocalDateTime.now());
//            user.setEmailVerified(true);
//            return user;
//        }
//
//        // Entity to DTO
//        public static SignUpRequest toSignupDto(User user) {
//            SignUpRequest dto = new SignUpRequest();
//            dto.setUsername(user.getUsername());
//            dto.setEmail(user.getEmail());
//            dto.setMobileNumber(user.getMobileNumber());
//            dto.setPassword(user.getPassword());
//            return dto;
//        }
//    }
//
//
//

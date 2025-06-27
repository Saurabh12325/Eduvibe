package com.example.demo.OAuth;

import com.example.demo.Entity.Provider;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.JWT.JwtUtils;
import com.example.demo.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String mobileNumber = oAuth2User.getAttribute("mobileNumber");

        Optional<User> existing = userRepository.findByEmail(email);
        System.out.println("Does user already exist? " + existing.isPresent());

        if (existing.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(name);
            newUser.setProvider(Provider.Google);
            newUser.setRole(Role.Student);
            newUser.setEmailVerified(true);
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setMobileNumber(mobileNumber);
            userRepository.save(newUser);


            System.out.println("New user saved to DB: " + email);
        }
            // Generate JWT token
            String token = jwtUtils.generateToken(email);

            // Return custom response
            Map<String, String> result = new HashMap<>();
            result.put("message", "OAuth login successful");
            result.put("token", token);
            result.put("email", email);

            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(result));


    }
}

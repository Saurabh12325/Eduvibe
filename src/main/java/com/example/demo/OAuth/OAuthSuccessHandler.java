package com.example.demo.OAuth;

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
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private JwtUtils jwtUtils;

    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {


            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            String email = user.getAttribute("email");

            String token = jwtUtils.generateToken(email);

            Map<String, String> result = new HashMap<>();
            result.put("message", "OAuth login successful");
            result.put("token", token);
            result.put("email", email);

            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(result));

    }
}

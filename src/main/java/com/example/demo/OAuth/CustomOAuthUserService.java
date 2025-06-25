package com.example.demo.OAuth;

import com.example.demo.Entity.Provider;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
@Component
@AllArgsConstructor

public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = new DefaultOAuth2UserService().loadUser(userRequest);
        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(name);
            newUser.setProvider(Provider.Google);
            newUser.setRole(Role.Student);
            newUser.setEmailVerified(true);
            newUser.setCreatedAt(LocalDateTime.now());

            userRepository.save(newUser);
        }

        return user;
    }
    }


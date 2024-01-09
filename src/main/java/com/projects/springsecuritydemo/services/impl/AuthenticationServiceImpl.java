package com.projects.springsecuritydemo.services.impl;

import com.projects.springsecuritydemo.dto.SignUpRequest;
import com.projects.springsecuritydemo.entities.Role;
import com.projects.springsecuritydemo.entities.User;
import com.projects.springsecuritydemo.repository.UserRepository;
import com.projects.springsecuritydemo.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User signUp(SignUpRequest sign){
        User user = new User();
        user.setEmail(sign.getEmail());
        user.setFirstName(sign.getFirstName());
        user.setLastName(sign.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(sign.getPassword()));

       return userRepository.save(user);
    }
}

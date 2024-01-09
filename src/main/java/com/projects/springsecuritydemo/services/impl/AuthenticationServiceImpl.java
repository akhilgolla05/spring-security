package com.projects.springsecuritydemo.services.impl;

import com.projects.springsecuritydemo.dto.JwtAuthenticationResponse;
import com.projects.springsecuritydemo.dto.SignInRequest;
import com.projects.springsecuritydemo.dto.SignUpRequest;
import com.projects.springsecuritydemo.entities.Role;
import com.projects.springsecuritydemo.entities.User;
import com.projects.springsecuritydemo.repository.UserRepository;
import com.projects.springsecuritydemo.services.AuthenticationService;
import com.projects.springsecuritydemo.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    //we can validate user email and password by Authentication Manager
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public User signUp(SignUpRequest sign){
        User user = new User();
        user.setEmail(sign.getEmail());
        user.setFirstName(sign.getFirstName());
        user.setLastName(sign.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(sign.getPassword()));

       return userRepository.save(user);
    }


    public JwtAuthenticationResponse signIn(SignInRequest sign){
        //this will validate the username and password, if its wrong will throw Exception.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(sign.getEmail(), sign.getPassword()));

        //after validation, we can fetch user from DB and use JWT service to generate Token

        var user = userRepository.findByEmail(sign.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid Email Exception"));

        //call JwtService for token
        var jwt = jwtService.generateToken(user);

        //refresh token
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse =
                new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;

    }
}

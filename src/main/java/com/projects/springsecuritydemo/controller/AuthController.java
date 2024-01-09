package com.projects.springsecuritydemo.controller;

import com.projects.springsecuritydemo.dto.JwtAuthenticationResponse;
import com.projects.springsecuritydemo.dto.SignInRequest;
import com.projects.springsecuritydemo.dto.SignUpRequest;
import com.projects.springsecuritydemo.entities.User;
import com.projects.springsecuritydemo.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest sign){

        return ResponseEntity.ok(authenticationService.signUp(sign));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest sign){

        return ResponseEntity.ok(authenticationService.signIn(sign));
    }
}

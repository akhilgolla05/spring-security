package com.projects.springsecuritydemo.services;

import com.projects.springsecuritydemo.dto.JwtAuthenticationResponse;
import com.projects.springsecuritydemo.dto.RefreshTokenRequest;
import com.projects.springsecuritydemo.dto.SignInRequest;
import com.projects.springsecuritydemo.dto.SignUpRequest;
import com.projects.springsecuritydemo.entities.User;

public interface AuthenticationService {

    User signUp(SignUpRequest sign);

    JwtAuthenticationResponse signIn(SignInRequest sign);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}

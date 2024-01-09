package com.projects.springsecuritydemo.services;

import com.projects.springsecuritydemo.dto.SignUpRequest;
import com.projects.springsecuritydemo.entities.User;

public interface AuthenticationService {

    User signUp(SignUpRequest sign);
}

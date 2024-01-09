package com.projects.springsecuritydemo.dto;

//response after sign-in

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String token;

    private String refreshToken;


}

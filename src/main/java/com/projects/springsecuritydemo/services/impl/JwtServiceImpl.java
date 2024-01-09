package com.projects.springsecuritydemo.services.impl;

import com.projects.springsecuritydemo.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    //We are generating Token By UserDetails
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 + 60 + 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    //This Method Will Return a Key
    private Key getSignKey(){
        byte[] key = Decoders.BASE64.decode("srCTukzbvO9iwOnfrcJWwPfsPWjGq+wiyWudW0Fsp40pDtNBxYWtkwt1Hnfg5kqt");
        return Keys.hmacShaKeyFor(key);
    }


    //now we to get the Username from Token
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }


    //we need to another Method to extract Username from the Token
    //for that we need to extract claims from the Token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){

        //here it will extract all claims
        final Claims claims = extractAllClaims(token);
        //from that claims, it extracts particulat claim
        return claimsResolvers.apply(claims);
    }

    //this Methiod Will return all the claims from the Token
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();
    }



    //this method checks the Token Validity
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        //checks if expiration is before current date
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


}

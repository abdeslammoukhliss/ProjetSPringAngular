package com.example.projetspringangularjwt.security;

import org.bouncycastle.crypto.tls.MACAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class    SecurityController {
    @Autowired
    private AuthenticationManager AuthenticationManager;

    @Autowired
    private JwtEncoder jwtEncoder;
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/logout")
    public String logout(){
        return "logout";
    }
    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        System.out.println(authentication);
        return authentication;
    }
    @PostMapping("/login")
    public Map<String, String> login( String username, String password){
        Authentication authentication= AuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        Instant instant=Instant.now();
        String authorities=authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder().
            issuedAt(instant)
                .expiresAt(instant.plusSeconds(3600))
                .subject(username)
                .claim("scope",authorities)
                .build();
        JwtEncoderParameters jwtEncoderParameters=JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                jwtClaimsSet
                );
        String jwt=jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("jwt-token",jwt);


    }
}

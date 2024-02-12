package com.krillinator.lektion_5.controllers;

import com.krillinator.lektion_5.Jwt.JwtAuthenticationResponse;
import com.krillinator.lektion_5.Jwt.JwtTokenGenerator;
import com.krillinator.lektion_5.models.user.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(JwtTokenGenerator jwtTokenGenerator, AuthenticationManager authenticationManager) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/debug")
    public ResponseEntity<LoginRequest> debugEndpoint(@RequestBody String requestBody) {
        // Print the received request body to the console for debugging
        System.out.println("Received request body: " + requestBody);

        // Return a simple response acknowledging the receipt of the request
        return ResponseEntity.ok().body(new LoginRequest("TEST", "TEST2"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user based on the credentials provided in the LoginRequest
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        // Generate JWT token using the authenticated Authentication object
        String token = jwtTokenGenerator.generateToken(authentication);

        // Return the token in the response
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }


}

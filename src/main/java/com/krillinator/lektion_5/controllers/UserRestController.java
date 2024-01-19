package com.krillinator.lektion_5.controllers;

import com.krillinator.lektion_5.config.AppPasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final AppPasswordConfig appPasswordConfig;

    @Autowired
    public UserRestController(AppPasswordConfig appPasswordConfig) {
        this.appPasswordConfig = appPasswordConfig;
    }

    @GetMapping("/hash")
    public ResponseEntity<String> testBcryptHash(
            @RequestParam(defaultValue = "", required = false) String continueParam,
            @RequestParam(defaultValue = "", required = false) String inputPassword
    ) {

        return new ResponseEntity<>(
                appPasswordConfig.bCryptPasswordEncoder().encode(inputPassword),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/helloAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> sayHelloToAdmin() {

        return new ResponseEntity<>("Hello ADMIN!", HttpStatus.ACCEPTED);
    }

    @GetMapping("/helloUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> sayHelloToUser() {

        return new ResponseEntity<>("Hello USER!", HttpStatus.ACCEPTED);
    }

    @GetMapping("/sayGet")
    @PreAuthorize("hasAuthority('GET')")
    public ResponseEntity<String> checkGetAuthority() {

        return new ResponseEntity<>("You can only enter with GET Authority!", HttpStatus.ACCEPTED);
    }



}

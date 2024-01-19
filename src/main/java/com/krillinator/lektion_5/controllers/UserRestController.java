package com.krillinator.lektion_5.controllers;

import com.krillinator.lektion_5.config.AppPasswordConfig;
import com.krillinator.lektion_5.models.user.UserEntity;
import com.krillinator.lektion_5.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* TODO - Problem Authentication for PostMapping
*
*
* */

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final AppPasswordConfig appPasswordConfig;
    private final UserRepository userRepository;

    @Autowired
    public UserRestController(AppPasswordConfig appPasswordConfig, UserRepository userRepository) {
        this.appPasswordConfig = appPasswordConfig;
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<UserEntity> createNewUser(@RequestBody UserEntity newUser) {

        UserEntity userEntity = new UserEntity(
                newUser.getUsername(),
                newUser.getPassword(),
                List.of(),
                newUser.isAccountNonExpired(),
                newUser.isAccountNonLocked(),
                newUser.isEnabled(),
                newUser.isCredentialsNonExpired()
        );

        return new ResponseEntity<>(userRepository.save(userEntity), HttpStatus.CREATED);
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

package com.krillinator.lektion_5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class AppPasswordConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);     // Default is 10
    }

/*
    public Pbkdf2PasswordEncoder test() {
        return new Pbkdf2PasswordEncoder(
                "",
                0,
                0,
                0);
    }

 */

}

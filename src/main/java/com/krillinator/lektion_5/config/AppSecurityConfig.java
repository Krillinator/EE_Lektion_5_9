package com.krillinator.lektion_5.config;


import com.krillinator.lektion_5.user.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.krillinator.lektion_5.user.Roles.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity               // Enables use of @PreAuthorize
public class AppSecurityConfig {

    // Info about Authentication & Authorities:
    // Authentication - identity (Are you who you say you are?) // I am Batman (Username & Password)
    // Authorities - Role & Permissions
    //      ROLE_ADMIN  == GET, POST, PUT, DELETE
    //      ROLE_BATMAN == GET, POST, PUT
    //      ROLE_USER   == GET, POST
    //      ROLE_GUEST  == GET

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/admin-page").hasRole(ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())   // Override /login
                // .httpBasic()
                .build();
    }

    // Override Users
    @Bean
    public UserDetailsService userDetailsService() {

        /* INPUT --> "BATMAN"
        *       ROLE_BATMAN
        *       ROLE_ADMIN
        *       ROLE_USER
        *       ROLE_GUEST
        * */

        // USER
        UserDetails frida = User.withDefaultPasswordEncoder()
                .username("frida")
                .password("123")
                .roles(USER.name())     // "USER"
                .authorities(USER.getPermissions())
                .build();

        // ADMIN
        UserDetails anton = User.withDefaultPasswordEncoder()
                .username("anton")
                .password("123")
                .roles(ADMIN.name())    // "ADMIN"
                .authorities(ADMIN.getPermissions())
                .build();

                return new InMemoryUserDetailsManager(frida, anton);
    }

}

package com.krillinator.lektion_5.config;

import com.krillinator.lektion_5.models.user.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.krillinator.lektion_5.models.user.Roles.*;

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

    // TODO - Talk about Deprecated stuff!


    private final AppPasswordConfig appPasswordConfig;

    @Autowired
    public AppSecurityConfig(AppPasswordConfig appPasswordConfig) {
        this.appPasswordConfig = appPasswordConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/hash").permitAll()
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
                .password(appPasswordConfig.bCryptPasswordEncoder().encode("123"))
                // .roles(USER.name())     // "USER"
                .authorities(USER.getPermissions())
                .build();

        // ADMIN
        UserDetails anton = User.builder()
                .username("anton")
                .password(appPasswordConfig.bCryptPasswordEncoder().encode("123"))
                .authorities(ADMIN.getAuthorities())
                .build();

                return new InMemoryUserDetailsManager(frida, anton);
    }

}

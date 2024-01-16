package com.krillinator.lektion_5.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/admin-page").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                // .httpBasic()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // USER
        UserDetails benny = User.withDefaultPasswordEncoder()
                .username("frida")
                .password("123")
                .roles("USER")
                .build();

        // ADMIN
        UserDetails anton = User.withDefaultPasswordEncoder()
                .username("anton")
                .password("123")
                .roles("ADMIN")
                .build();

                return new InMemoryUserDetailsManager(benny, anton);
    }

}

package com.proyecto.TIS.users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // bean para encriptar la contra del usuario con bcrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // apagar csrf pq usamos jwt y no nos importa ahorita
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // el login no pide token
                .anyRequest().permitAll() // todo lo demas de usuarios pasa libre por ahora
            );
        return http.build();
    }
}

// Arquivo: src/main/java/com/exemplo/demo/config/SecurityConfig.java
package com.rubem.config;

import com.rubem.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/pessoa/**").permitAll()
                        .requestMatchers("/pessoa").permitAll()
                        .requestMatchers("/aluno").permitAll()
                        .requestMatchers("/aluno/**").permitAll()
                        .requestMatchers("/professor/**").hasRole("ATENDENTE")
                        .requestMatchers("/professor/login").permitAll()
                        .requestMatchers("/professor").hasRole("ATENDENTE")
                        .requestMatchers("/funcionario/**").permitAll()
                        .requestMatchers("/funcionario").permitAll()
                        .requestMatchers("/me").authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

package com.springsecurity.example.config;

import com.springsecurity.example.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    String[] publicEndpoints={
            "/api/v1/welcome/hello",
            "/api/v1/auth/signup",
            "/api/v1/auth/login"
    };

    @Autowired
    private JWTFilter filter;

    @Bean
    public SecurityFilterChain securityConfiguration(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(publicEndpoints).permitAll()
                        .requestMatchers("/api/v1/admin/welcome").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authProvider(
            CustomerUserDetailsService customerUserDetailsService,
            PasswordEncoder passwordEncoder){

        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(customerUserDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }
}

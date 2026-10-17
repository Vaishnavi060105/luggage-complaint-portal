package com.college.luggageportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/** Only /reviewer/** needs a login. Everything else (including /actuator/health) is public. */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(a -> a
                .requestMatchers("/reviewer/**").hasRole("REVIEWER")
                .anyRequest().permitAll())
            .formLogin(f -> f.loginPage("/login").defaultSuccessUrl("/reviewer/complaints", true).permitAll())
            .logout(l -> l.logoutSuccessUrl("/"))
            // The public passenger form is anonymous, so it is exempt from CSRF tokens.
            .csrf(c -> c.ignoringRequestMatchers("/complaints"));
        return http.build();
    }
}

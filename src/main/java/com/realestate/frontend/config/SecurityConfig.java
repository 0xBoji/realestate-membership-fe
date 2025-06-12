package com.realestate.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/home", "/login", "/register", "/properties", "/properties/**", 
                               "/categories", "/categories/**", "/webjars/**", "/css/**", "/js/**", 
                               "/images/**", "/static/**", "/auth/**", "/dashboard/**", 
                               "/my-properties/**", "/memberships/**", "/chat/**", "/profile/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable()) // Disable Spring Security's default form login
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        
        return http.build();
    }
}
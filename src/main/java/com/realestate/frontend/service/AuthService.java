package com.realestate.frontend.service;

import com.realestate.frontend.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AuthService {
    
    @Autowired
    private WebClient webClient;
    
    public Mono<AuthResponse> login(String username, String password) {
        Map<String, String> loginRequest = Map.of(
            "username", username,
            "password", password
        );
        
        return webClient.post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .onErrorReturn(new AuthResponse()); // Return empty response on error
    }
    
    public Mono<AuthResponse> register(String username, String email, String password, String fullName) {
        Map<String, String> registerRequest = Map.of(
            "username", username,
            "email", email,
            "password", password,
            "fullName", fullName
        );
        
        return webClient.post()
                .uri("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .onErrorReturn(new AuthResponse()); // Return empty response on error
    }
}
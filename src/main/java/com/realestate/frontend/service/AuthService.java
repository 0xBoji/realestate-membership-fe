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
            "usernameOrEmail", username,  // Fixed: backend expects usernameOrEmail
            "password", password
        );
        
        System.out.println("Calling API: POST /auth/login with usernameOrEmail: " + username);
        
        return webClient.post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .doOnSuccess(response -> {
                    System.out.println("API Response Success: " + (response != null ? "received" : "null"));
                    if (response != null) {
                        System.out.println("Response token: " + (response.getToken() != null ? "exists" : "null"));
                    }
                })
                .doOnError(error -> {
                    System.out.println("API Error: " + error.getMessage());
                    if (error.getMessage().contains("400")) {
                        System.out.println("🚨 400 Bad Request - Check username/password or user existence");
                    }
                })
                .onErrorReturn(new AuthResponse()); // Return empty response on error
    }
    
    public Mono<AuthResponse> register(String username, String email, String password, String fullName) {
        // Split fullName into firstName and lastName
        String[] nameParts = fullName.trim().split("\\s+", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";
        
        Map<String, String> registerRequest = Map.of(
            "username", username,
            "email", email,
            "password", password,
            "firstName", firstName,  // Fixed: backend expects firstName
            "lastName", lastName     // Fixed: backend expects lastName
        );
        
        System.out.println("Calling API: POST /auth/register with username: " + username);
        
        return webClient.post()
                .uri("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .doOnSuccess(response -> {
                    System.out.println("Register API Response Success: " + (response != null ? "received" : "null"));
                })
                .doOnError(error -> {
                    System.out.println("Register API Error: " + error.getMessage());
                })
                .onErrorReturn(new AuthResponse()); // Return empty response on error
    }
}
package com.realestate.frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.frontend.model.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class MembershipService {
    
    @Autowired
    private WebClient webClient;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public Mono<List<Membership>> getAllMemberships() {
        return webClient.get()
                .uri("/memberships")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, new TypeReference<List<Membership>>() {});
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Collections.emptyList();
                    }
                })
                .onErrorReturn(Collections.emptyList());
    }
    
    public Mono<Membership> getMembershipById(Long id) {
        return webClient.get()
                .uri("/memberships/{id}", id)
                .retrieve()
                .bodyToMono(Membership.class)
                .onErrorReturn(new Membership());
    }
    
    public Mono<String> subscribeMembership(String token, Long membershipId) {
        return webClient.post()
                .uri("/memberships/{membershipId}/subscribe", membershipId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("Error occurred while subscribing");
    }
    
    public Mono<Object> getMyMembership(String token) {
        return webClient.get()
                .uri("/memberships/my-membership")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorReturn(new Object());
    }
    
    public Mono<List<Object>> getMembershipHistory(String token) {
        return webClient.get()
                .uri("/memberships/my-history")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, new TypeReference<List<Object>>() {});
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Collections.emptyList();
                    }
                })
                .onErrorReturn(Collections.emptyList());
    }
}
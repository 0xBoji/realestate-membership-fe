package com.realestate.frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.frontend.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {
    
    @Autowired
    private WebClient webClient;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public Mono<List<Category>> getAllCategories() {
        return webClient.get()
                .uri("/categories")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        TypeReference<List<Category>> typeRef = new TypeReference<List<Category>>() {};
                        List<Category> categories = objectMapper.readValue(response, typeRef);
                        return categories;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Collections.<Category>emptyList();
                    }
                })
                .onErrorReturn(Collections.<Category>emptyList());
    }
    
    public Mono<Category> getCategoryById(Long id) {
        return webClient.get()
                .uri("/categories/{id}", id)
                .retrieve()
                .bodyToMono(Category.class)
                .onErrorReturn(new Category());
    }
}
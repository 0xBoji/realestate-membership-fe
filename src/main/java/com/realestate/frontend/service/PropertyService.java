package com.realestate.frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.frontend.model.PageResponse;
import com.realestate.frontend.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class PropertyService {
    
    @Autowired
    private WebClient webClient;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public Mono<PageResponse<Property>> getAllProperties(int page, int size, String sortBy, String sortDir) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/properties")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParam("sortBy", sortBy)
                        .queryParam("sortDir", sortDir)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, new TypeReference<PageResponse<Property>>() {});
                    } catch (Exception e) {
                        e.printStackTrace();
                        PageResponse<Property> emptyResponse = new PageResponse<>();
                        emptyResponse.setContent(Collections.emptyList());
                        return emptyResponse;
                    }
                })
                .onErrorReturn(new PageResponse<>());
    }
    
    public Mono<Property> getPropertyById(Long id) {
        return webClient.get()
                .uri("/properties/{id}", id)
                .retrieve()
                .bodyToMono(Property.class)
                .onErrorReturn(new Property());
    }
    
    public Mono<List<Property>> getFeaturedProperties(int limit) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/properties/featured")
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, new TypeReference<List<Property>>() {});
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Collections.emptyList();
                    }
                })
                .onErrorReturn(Collections.emptyList());
    }
    
    public Mono<PageResponse<Property>> searchProperties(String city, String district, String propertyType, 
                                                        String listingType, String minPrice, String maxPrice, 
                                                        Long categoryId, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/properties/search")
                            .queryParam("page", page)
                            .queryParam("size", size);
                    
                    if (city != null && !city.isEmpty()) {
                        builder.queryParam("city", city);
                    }
                    if (district != null && !district.isEmpty()) {
                        builder.queryParam("district", district);
                    }
                    if (propertyType != null && !propertyType.isEmpty()) {
                        builder.queryParam("propertyType", propertyType);
                    }
                    if (listingType != null && !listingType.isEmpty()) {
                        builder.queryParam("listingType", listingType);
                    }
                    if (minPrice != null && !minPrice.isEmpty()) {
                        builder.queryParam("minPrice", minPrice);
                    }
                    if (maxPrice != null && !maxPrice.isEmpty()) {
                        builder.queryParam("maxPrice", maxPrice);
                    }
                    if (categoryId != null) {
                        builder.queryParam("categoryId", categoryId);
                    }
                    
                    return builder.build();
                })
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, new TypeReference<PageResponse<Property>>() {});
                    } catch (Exception e) {
                        e.printStackTrace();
                        PageResponse<Property> emptyResponse = new PageResponse<>();
                        emptyResponse.setContent(Collections.emptyList());
                        return emptyResponse;
                    }
                })
                .onErrorReturn(new PageResponse<>());
    }
    
    public Mono<PageResponse<Property>> getMyProperties(String token, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/properties/my-properties")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, new TypeReference<PageResponse<Property>>() {});
                    } catch (Exception e) {
                        e.printStackTrace();
                        PageResponse<Property> emptyResponse = new PageResponse<>();
                        emptyResponse.setContent(Collections.emptyList());
                        return emptyResponse;
                    }
                })
                .onErrorReturn(new PageResponse<>());
    }
}
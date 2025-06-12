package com.realestate.frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    @Value("${api.base.url:http://ec2-13-54-93-195.ap-southeast-2.compute.amazonaws.com:8080/api/v1}")
    private String apiBaseUrl;
    
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(apiBaseUrl)
                .build();
    }
}
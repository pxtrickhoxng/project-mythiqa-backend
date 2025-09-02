package com.mythiqa.mythiqabackend.service;

import com.mythiqa.mythiqabackend.exception.UsernameAlreadyTakenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClerkService {
    private final WebClient webClient;

    public ClerkService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Value("${clerk.secret-key}")
    private String clerkSecret;

    public void updateClerkUsername(String userId, String newUsername) {
        try {
            webClient
                    .patch()
                    .uri("https://api.clerk.com/v1/users/{userId}", userId)
                    .headers(headers -> {
                        headers.setBearerAuth(clerkSecret);
                        headers.setContentType(MediaType.APPLICATION_JSON);
                    })
                    .bodyValue(Map.of("username", newUsername))
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new UsernameAlreadyTakenException(newUsername);
            } else {
                throw new RuntimeException("Failed to update Clerk user: " + e.getResponseBodyAsString(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error has occurred", e);
        }
    }
}

package com.petproject.songguessr.client;

import com.petproject.outrequest.SearchGeniusResponse;
import com.petproject.outrequest.SongsGeniusResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Configuration
public class GeniusWebClient implements GeniusClient {
    private final WebClient webClient;

    public GeniusWebClient(@Value("${service.genius.client.host}") final String host,
                           @Value("${service.genius.client.token}") final String token) {
        final String bearer = String.format("Bearer %s", token);
        this.webClient = WebClient.builder()
                .baseUrl(host)
                .defaultHeader(HttpHeaders.AUTHORIZATION, bearer)
                .build();
    }

    public SearchGeniusResponse searchByText(String text) throws WebClientResponseException {
        return webClient.get()
                .uri(String.format("/search?q=%s", text))
                .retrieve()
                .bodyToMono(SearchGeniusResponse.class)
                .block();
    }

    public SongsGeniusResponse searchBySongId(String id) throws WebClientResponseException {
        return webClient.get()
                .uri(String.format("/songs/%s?text_format=plain", id))
                .retrieve()
                .bodyToMono(SongsGeniusResponse.class)
                .block();
    }
}

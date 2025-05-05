package com.petproject.musicguessr.client;

import com.petproject.musicguessr.model.outrequest.SearchGeniusResponse;
import com.petproject.musicguessr.model.outrequest.SongsGeniusResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

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

    public SearchGeniusResponse searchByText(String text) {
        return webClient.get()
                .uri(String.format("/search?q=%s", text))
                .retrieve()
                .bodyToMono(SearchGeniusResponse.class)
                .block();
    }

    public SongsGeniusResponse searchBySongId(String id) {
        return webClient.get()
                .uri(String.format("/songs/%s?text_format=plain", id))
                .retrieve()
                .bodyToMono(SongsGeniusResponse.class)
                .block();
    }
}

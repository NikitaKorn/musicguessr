package com.petproject.songguessr.service.genius;

import com.petproject.outrequest.Hit;
import com.petproject.outrequest.Song;
import com.petproject.songguessr.client.GeniusClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public final class GeniusServiceImpl implements GeniusService {
    private GeniusClient geniusClient;

    @Override
    public List<Hit> findByText(String text) {
        List<Hit> findResults = Collections.emptyList();
        try {
            findResults = geniusClient.searchByText(text).getResponse().getHits();
        } catch (WebClientResponseException ex) {
            log.error("Can't load songs from Genius: {}", ex.getMessage());
            log.debug("Error: ", ex);
        }
        return findResults.stream()
                .filter(hit -> hit.getType().equals("song"))
                .toList();
    }

    @Override
    public Optional<Song> findSongById(String id) {
        Song findResults = null;
        try {
            findResults = geniusClient.searchBySongId(id).getResponse().getSong();
        } catch (WebClientResponseException ex) {
            log.error("Can't load song from Genius: {}", ex.getMessage());
            log.debug("Error: ", ex);
        }
        return Optional.ofNullable(findResults);
    }
}

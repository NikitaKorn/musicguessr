package com.petproject.songguessr.service.genius;

import com.petproject.songguessr.client.GeniusClient;
import com.petproject.songguessr.model.outrequest.Hit;
import com.petproject.songguessr.model.outrequest.Song;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public final class GeniusServiceImpl implements GeniusService {
    private GeniusClient webClient;

    @Override
    public List<Hit> findByText(String text) {
        List<Hit> findResults = webClient.searchByText(text).getResponse().getHits();
        return findResults.stream()
                .filter(hit -> hit.getType().equals("song"))
                .toList();
    }

    @Override
    public Song findSongById(String id) {
        return webClient.searchBySongId(id)
                .getResponse()
                .getSong();
    }
}

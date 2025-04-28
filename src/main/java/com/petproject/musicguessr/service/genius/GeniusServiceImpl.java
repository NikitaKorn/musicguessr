package com.petproject.musicguessr.service.genius;

import com.petproject.musicguessr.client.GeniusClient;
import com.petproject.musicguessr.model.outrequest.Hit;
import com.petproject.musicguessr.model.outrequest.Song;
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

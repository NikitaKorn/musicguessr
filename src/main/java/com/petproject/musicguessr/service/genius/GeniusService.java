package com.petproject.musicguessr.service.genius;

import com.petproject.musicguessr.model.outrequest.Hit;
import com.petproject.musicguessr.model.outrequest.Song;

import java.util.List;

public interface GeniusService {
    List<Hit> findByText(String text);
    Song findSongById(String id);
}

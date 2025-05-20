package com.petproject.songguessr.service.genius;

import com.petproject.songguessr.model.outrequest.Hit;
import com.petproject.songguessr.model.outrequest.Song;

import java.util.List;

public interface GeniusService {
    List<Hit> findByText(String text);
    Song findSongById(String id);
}

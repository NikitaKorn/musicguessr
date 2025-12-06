package com.petproject.songguessr.service.genius;

import com.petproject.outrequest.Hit;
import com.petproject.outrequest.Song;

import java.util.List;

public interface GeniusService {
    List<Hit> findByText(String text);
    Song findSongById(String id);
}

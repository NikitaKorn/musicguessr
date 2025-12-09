package com.petproject.songguessr.service.genius;

import com.petproject.outrequest.Hit;
import com.petproject.outrequest.Song;

import java.util.List;
import java.util.Optional;

public interface GeniusService {
    List<Hit> findByText(String text);
    Optional<Song> findSongById(String id);
}

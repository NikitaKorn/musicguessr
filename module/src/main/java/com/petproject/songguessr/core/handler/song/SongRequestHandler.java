package com.petproject.songguessr.core.handler.song;

import com.petproject.outrequest.Song;
import com.petproject.songguessr.exception.SongNotFoundException;
import com.petproject.songguessr.service.genius.GeniusService;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.Optional;

@AllArgsConstructor
public abstract class SongRequestHandler {
    private final GeniusService geniusService;

    protected Song findSongAndSort(String message) {
        Song song = geniusService.findSongById(message)
                .orElseThrow(() -> new SongNotFoundException("Can't find song by id " + message));
        song.getMedia().sort(Comparator.comparingInt(Song.Media::getTypePriority));
        return song;
    }
}

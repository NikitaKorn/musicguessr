package com.petproject.musicguessr.core.handler.broadcast;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.inrequest.RequestEvent;
import com.petproject.musicguessr.model.outrequest.Song;
import com.petproject.musicguessr.model.response.dto.SongResultEvent;
import com.petproject.musicguessr.service.genius.GeniusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Set;

import static com.petproject.musicguessr.model.EventType.SONG_REQUEST;

@Component
@AllArgsConstructor
public class SongRequestHandlerBroadcast implements BroadcastEventHandler {
    private final EventDispatcher eventDispatcher;
    private final GeniusService geniusService;

    @Override
    public boolean canHandle(RequestEvent event) {
        return SONG_REQUEST.equals(event.getEventType());
    }

    @Override
    public void handle(RequestEvent event, Set<Player> players) {
        Song song = findSongAndSort(event.getMessage());
        eventDispatcher.broadcastEvent(new SongResultEvent(song), players);
    }

    private Song findSongAndSort(String message){
        Song song = geniusService.findSongById(message);
        song.getMedia().sort(Comparator.comparingInt(Song.Media::getTypePriority));
        return song;
    }
}

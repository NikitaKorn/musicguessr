package com.petproject.musicguessr.core.handler.song;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.TargetEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.inrequest.SongRequestEvent;
import com.petproject.musicguessr.model.response.dto.SongResultResponseEvent;
import com.petproject.musicguessr.service.genius.GeniusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.petproject.musicguessr.model.EventType.SONG_REQUEST_TARGET;

@Component
public final class SongRequestHandlerTarget extends SongRequestHandler implements TargetEventHandler<SongRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public SongRequestHandlerTarget(@Autowired EventDispatcher eventDispatcher, @Autowired GeniusService geniusService) {
        super(geniusService);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(SongRequestEvent event) {
        return SONG_REQUEST_TARGET.equals(event.getEventType());
    }

    @Override
    public void handle(SongRequestEvent event, Player player) {
        var song = findSongAndSort(event.getPayload().getMessage());
        eventDispatcher.sendEventToPlayers(new SongResultResponseEvent(song), Collections.singleton(player));
    }
}

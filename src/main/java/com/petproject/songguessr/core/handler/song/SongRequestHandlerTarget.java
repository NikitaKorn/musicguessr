package com.petproject.songguessr.core.handler.song;

import com.petproject.songguessr.core.dispatcher.EventDispatcher;
import com.petproject.songguessr.core.handler.TargetEventHandler;
import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.inrequest.SongRequestEvent;
import com.petproject.songguessr.model.response.dto.SongResultResponseEvent;
import com.petproject.songguessr.service.genius.GeniusService;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.petproject.songguessr.model.EventType.SONG_REQUEST_TARGET;

@Component
public final class SongRequestHandlerTarget extends SongRequestHandler implements TargetEventHandler<SongRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public SongRequestHandlerTarget(EventDispatcher eventDispatcher, GeniusService geniusService) {
        super(geniusService);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(BaseEvent<?> event) {
        return SONG_REQUEST_TARGET.equals(event.getEventType());
    }

    @Override
    public void handle(SongRequestEvent event, Player player) {
        var song = findSongAndSort(event.getPayload().getMessage());
        eventDispatcher.sendEventToPlayers(new SongResultResponseEvent(song), Collections.singleton(player));
    }

    @Override
    public Class<SongRequestEvent> getType() {
        return SongRequestEvent.class;
    }
}

package com.petproject.songguessr.core.handler.song;

import com.petproject.BaseEvent;
import com.petproject.inrequest.SongRequestEvent;
import com.petproject.response.dto.SongResultResponseEvent;
import com.petproject.songguessr.core.dispatcher.EventDispatcher;
import com.petproject.songguessr.core.handler.BroadcastEventHandler;
import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.service.genius.GeniusService;

import java.util.Set;

import static com.petproject.EventType.SONG_REQUEST_BROADCAST;

public final class SongRequestHandlerBroadcast extends SongRequestHandler implements BroadcastEventHandler<SongRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public SongRequestHandlerBroadcast(EventDispatcher eventDispatcher, GeniusService geniusService) {
        super(geniusService);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(BaseEvent<?> event) {
        return SONG_REQUEST_BROADCAST.equals(event.getEventType());
    }

    @Override
    public void handle(SongRequestEvent event, Set<Player> players) {
        var song = findSongAndSort(event.getPayload().getMessage());
        eventDispatcher.sendEventToPlayers(new SongResultResponseEvent(song), players);
    }

    @Override
    public Class<SongRequestEvent> getType() {
        return SongRequestEvent.class;
    }
}

package com.petproject.musicguessr.core.handler.song;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.BroadcastEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.inrequest.SongRequestEvent;
import com.petproject.musicguessr.model.response.dto.SongResultResponseEvent;
import com.petproject.musicguessr.service.genius.GeniusService;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.petproject.musicguessr.model.EventType.SONG_REQUEST_BROADCAST;

@Component
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

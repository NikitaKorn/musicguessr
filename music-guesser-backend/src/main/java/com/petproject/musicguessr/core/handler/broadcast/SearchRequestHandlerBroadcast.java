package com.petproject.musicguessr.core.handler.broadcast;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.inrequest.RequestEvent;
import com.petproject.musicguessr.model.outrequest.Hit;
import com.petproject.musicguessr.model.response.dto.SearchResultEvent;
import com.petproject.musicguessr.service.genius.GeniusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.petproject.musicguessr.model.EventType.SEARCH_REQUEST;

@AllArgsConstructor
@Component
public class SearchRequestHandlerBroadcast implements BroadcastEventHandler {
    private final EventDispatcher eventDispatcher;
    private final GeniusService geniusService;

    @Override
    public boolean canHandle(RequestEvent event) {
        return SEARCH_REQUEST.equals(event.getEventType());
    }

    @Override
    public void handle(RequestEvent event, Set<Player> players) {
        List<Hit> hits = geniusService.findByText(event.getMessage());
        eventDispatcher.broadcastEvent(new SearchResultEvent(hits), players);
    }
}

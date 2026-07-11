package com.petproject.songguessr.core.handler.search;

import com.petproject.BaseEvent;
import com.petproject.inrequest.SearchRequestEvent;
import com.petproject.outrequest.Hit;
import com.petproject.response.dto.SearchResponseEvent;
import com.petproject.songguessr.core.dispatcher.EventDispatcher;
import com.petproject.songguessr.core.handler.BroadcastEventHandler;
import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.service.genius.GeniusService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.petproject.EventType.SEARCH_REQUEST_BROADCAST;

@Component
public final class SearchRequestHandlerBroadcast extends SearchRequestHandler implements BroadcastEventHandler<SearchRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public SearchRequestHandlerBroadcast(EventDispatcher eventDispatcher, GeniusService geniusService) {
        super(geniusService);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(BaseEvent<?> event) {
        return SEARCH_REQUEST_BROADCAST.equals(event.getEventType());
    }

    @Override
    public void handle(SearchRequestEvent event, Set<Player> players) {
        List<Hit> search = search(event);
        eventDispatcher.sendEventToPlayers(new SearchResponseEvent(search), players);
    }

    @Override
    public Class<SearchRequestEvent> getType() {
        return SearchRequestEvent.class;
    }
}

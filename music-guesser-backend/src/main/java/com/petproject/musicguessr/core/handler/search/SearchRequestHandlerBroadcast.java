package com.petproject.musicguessr.core.handler.search;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.BroadcastEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.inrequest.SearchRequestEvent;
import com.petproject.musicguessr.model.outrequest.Hit;
import com.petproject.musicguessr.model.response.dto.SearchResponseEvent;
import com.petproject.musicguessr.service.genius.GeniusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.petproject.musicguessr.model.EventType.SEARCH_REQUEST_BROADCAST;

@Component
public final class SearchRequestHandlerBroadcast extends SearchRequestHandler implements BroadcastEventHandler<SearchRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public SearchRequestHandlerBroadcast(@Autowired EventDispatcher eventDispatcher,
                                         @Autowired GeniusService geniusService) {
        super(geniusService);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(SearchRequestEvent event) {
        return SEARCH_REQUEST_BROADCAST.equals(event.getEventType());
    }

    @Override
    public void handle(SearchRequestEvent event, Set<Player> players) {
        List<Hit> search = search(event);
        eventDispatcher.sendEventToPlayers(new SearchResponseEvent(search), players);
    }
}

package com.petproject.songguessr.core.handler.search;

import com.petproject.songguessr.core.dispatcher.EventDispatcher;
import com.petproject.songguessr.core.handler.TargetEventHandler;
import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.inrequest.SearchRequestEvent;
import com.petproject.songguessr.model.response.dto.SearchResponseEvent;
import com.petproject.songguessr.service.genius.GeniusService;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.petproject.songguessr.model.EventType.SEARCH_REQUEST_TARGET;

@Component
public final class SearchRequestHandlerTarget extends SearchRequestHandler implements TargetEventHandler<SearchRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public SearchRequestHandlerTarget(EventDispatcher eventDispatcher, GeniusService geniusService) {
        super(geniusService);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(BaseEvent<?> event) {
        return SEARCH_REQUEST_TARGET.equals(event.getEventType());
    }

    @Override
    public void handle(SearchRequestEvent event, Player player) {
        var hits = search(event);
        eventDispatcher.sendEventToPlayers(new SearchResponseEvent(hits), Collections.singleton(player));
    }

    @Override
    public Class<SearchRequestEvent> getType() {
        return SearchRequestEvent.class;
    }
}

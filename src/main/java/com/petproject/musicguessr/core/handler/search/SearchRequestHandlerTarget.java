package com.petproject.musicguessr.core.handler.search;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.TargetEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.inrequest.SearchRequestEvent;
import com.petproject.musicguessr.model.response.dto.SearchResponseEvent;
import com.petproject.musicguessr.service.genius.GeniusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.petproject.musicguessr.model.EventType.SEARCH_REQUEST_TARGET;

@Component
public final class SearchRequestHandlerTarget extends SearchRequestHandler implements TargetEventHandler<SearchRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public SearchRequestHandlerTarget(@Autowired EventDispatcher eventDispatcher,
                                      @Autowired GeniusService geniusService) {
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

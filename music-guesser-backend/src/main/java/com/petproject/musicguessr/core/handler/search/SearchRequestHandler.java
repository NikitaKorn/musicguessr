package com.petproject.musicguessr.core.handler.search;

import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.inrequest.SearchRequestEvent;
import com.petproject.musicguessr.model.outrequest.Hit;
import com.petproject.musicguessr.service.genius.GeniusService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public abstract class SearchRequestHandler {
    private final GeniusService geniusService;

    protected  List<Hit> search(BaseEvent<SearchRequestEvent.Payload> event) {
        SearchRequestEvent.Payload payload = event.getPayload();
        return geniusService.findByText(payload.getMessage());
    }
}

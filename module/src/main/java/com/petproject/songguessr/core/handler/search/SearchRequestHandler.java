package com.petproject.songguessr.core.handler.search;

import com.petproject.BaseEvent;
import com.petproject.inrequest.SearchRequestEvent;
import com.petproject.outrequest.Hit;
import com.petproject.songguessr.service.genius.GeniusService;
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

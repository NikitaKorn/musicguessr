package com.petproject.songguessr.model.response.dto;

import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.EventType;
import com.petproject.songguessr.model.outrequest.Hit;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SearchResponseEvent extends BaseEvent<SearchResponseEvent.Payload> {
    private Payload payload;

    public SearchResponseEvent(List<Hit> hits) {
        this.eventType = EventType.SEARCH_RESPONSE;
        this.payload = new Payload(new ArrayList<>(hits));
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Payload {
        private List<Hit> hits;
    }
}

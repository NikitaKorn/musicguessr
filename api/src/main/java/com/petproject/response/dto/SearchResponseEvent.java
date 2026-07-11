package com.petproject.response.dto;

import com.petproject.BaseEvent;
import com.petproject.EventType;
import com.petproject.outrequest.Hit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

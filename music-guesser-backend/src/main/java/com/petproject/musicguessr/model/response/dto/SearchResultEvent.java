package com.petproject.musicguessr.model.response.dto;

import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import com.petproject.musicguessr.model.outrequest.Hit;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SearchResultEvent extends BaseEvent {
    private Payload payload;

    public SearchResultEvent(List<Hit> hits) {
        this.eventType = EventType.SEARCH_RESPONSE;
        this.payload = this.new Payload(new ArrayList<>(hits));
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class Payload {
        private List<Hit> hits;
    }
}

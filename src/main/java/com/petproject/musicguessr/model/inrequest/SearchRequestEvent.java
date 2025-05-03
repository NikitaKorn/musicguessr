package com.petproject.musicguessr.model.inrequest;

import com.petproject.musicguessr.model.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SearchRequestEvent extends BaseEvent<SearchRequestEvent.Payload> {
    private Payload payload;

    @Override
    public Payload getPayload() {
        return payload;
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Payload {
        private String message;
    }
}

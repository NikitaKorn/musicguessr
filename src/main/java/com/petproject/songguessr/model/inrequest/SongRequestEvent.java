package com.petproject.songguessr.model.inrequest;

import com.petproject.songguessr.model.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SongRequestEvent extends BaseEvent<SongRequestEvent.Payload> {
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
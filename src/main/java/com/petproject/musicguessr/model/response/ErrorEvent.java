package com.petproject.musicguessr.model.response;

import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
public class ErrorEvent extends BaseEvent<ErrorEvent.Payload> {
    private Payload payload;

    public ErrorEvent(String error) {
        this.eventType = EventType.ERROR;
        this.payload = new Payload(error);
    }

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

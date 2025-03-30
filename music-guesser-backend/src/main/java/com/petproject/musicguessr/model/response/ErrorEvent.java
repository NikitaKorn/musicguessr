package com.petproject.musicguessr.model.response;

import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ErrorEvent extends BaseEvent {
    private Payload payload;

    public ErrorEvent(String error) {
        this.eventType = EventType.ERROR;
        this.payload = new Payload(error);
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class Payload {
        private String error;
    }
}

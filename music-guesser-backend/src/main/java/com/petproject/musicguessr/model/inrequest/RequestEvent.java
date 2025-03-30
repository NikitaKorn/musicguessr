package com.petproject.musicguessr.model.inrequest;

import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestEvent extends BaseEvent {
    private Payload payload;

    public String getMessage() {
        if(payload.getMessage() != null){
            return payload.getMessage();
        } else {
            return "";
        }
    }

    public static RequestEvent empty() {
        RequestEvent requestEvent = new RequestEvent();
        requestEvent.eventType = EventType.NONE;
        requestEvent.payload = requestEvent.new Payload("");
        return requestEvent;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Payload {
        private String message;
    }
}

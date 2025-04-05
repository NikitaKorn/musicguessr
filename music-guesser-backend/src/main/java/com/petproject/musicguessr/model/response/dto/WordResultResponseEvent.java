package com.petproject.musicguessr.model.response.dto;

import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class WordResultResponseEvent extends BaseEvent {
    private Payload payload;

    public WordResultResponseEvent(String word) {
        this.eventType = EventType.WORD_RESPONSE;
        this.payload = new Payload(word);
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class Payload {
        private String word;
    }
}

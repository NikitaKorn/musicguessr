package com.petproject.musicguessr.model.response.dto;

import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ShowInviteCodeEvent extends BaseEvent {
    private Payload payload;

    public ShowInviteCodeEvent(String word) {
        this.eventType = EventType.SHOW_INVITE_CODE_RESPONSE;
        this.payload = new Payload(word);
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class Payload {
        private String code;
    }
}

package com.petproject.songguessr.model.response.dto;

import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ShowInviteCodeResponseEvent extends BaseEvent<ShowInviteCodeResponseEvent.Payload> {
    private Payload payload;

    public ShowInviteCodeResponseEvent(String word) {
        this.eventType = EventType.SHOW_INVITE_CODE_RESPONSE;
        this.payload = new Payload(word);
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Payload {
        private String code;
    }
}

package com.petproject.musicguessr.core.handler.target;

import com.petproject.musicguessr.config.GameSessionRoomRegistry;
import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.model.inrequest.RequestEvent;
import com.petproject.musicguessr.model.response.dto.ShowInviteCodeEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import static com.petproject.musicguessr.model.EventType.SHOW_INVITE_CODE_REQUEST;

@AllArgsConstructor
@Component
public class ShowInviteCodeRequestHandlerTarget implements TargetEventHandler {
    private final EventDispatcher eventDispatcher;
    private final GameSessionRoomRegistry roomRegistry;

    @Override
    public boolean canHandle(RequestEvent event) {
        return SHOW_INVITE_CODE_REQUEST.equals(event.getEventType());
    }

    @Override
    public void handle(RequestEvent event, WebSocketSession session) {
        String inviteCode = roomRegistry.findRoomBySession(session).getInviteCode();
        eventDispatcher.sendEventToClient(new ShowInviteCodeEvent(inviteCode), session);
    }
}

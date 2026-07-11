package com.petproject.songguessr.core.handler.code;

import com.petproject.BaseEvent;
import com.petproject.inrequest.CodeRequestEvent;
import com.petproject.response.dto.ShowInviteCodeResponseEvent;
import com.petproject.songguessr.core.dispatcher.EventDispatcher;
import com.petproject.songguessr.core.handler.TargetEventHandler;
import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.service.registry.GameRoomsRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;

import static com.petproject.EventType.SHOW_INVITE_CODE_REQUEST_TARGET;

@Component
public final class ShowInviteCodeRequestHandlerTarget extends ShowInviteCodeRequestHandler implements TargetEventHandler<CodeRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public ShowInviteCodeRequestHandlerTarget(GameRoomsRegistry roomRegistry, EventDispatcher eventDispatcher) {
        super(roomRegistry);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(BaseEvent<?> event) {
        return SHOW_INVITE_CODE_REQUEST_TARGET.equals(event.getEventType());
    }

    @Override
    public void handle(CodeRequestEvent event, Player player) {
        WebSocketSession session = player.getSession();
        String inviteCode = roomRegistry.findRoomByPlayerSession(session).getRoom().getInviteCode();
        eventDispatcher.sendEventToPlayers(new ShowInviteCodeResponseEvent(inviteCode), Collections.singleton(player));
    }

    @Override
    public Class<CodeRequestEvent> getType() {
        return CodeRequestEvent.class;
    }
}

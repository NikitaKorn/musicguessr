package com.petproject.musicguessr.core.handler.code;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.TargetEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.inrequest.CodeRequestEvent;
import com.petproject.musicguessr.model.response.dto.ShowInviteCodeResponseEvent;
import com.petproject.musicguessr.service.registry.GameRoomsRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;

import static com.petproject.musicguessr.model.EventType.SHOW_INVITE_CODE_REQUEST_TARGET;

@Component
public final class ShowInviteCodeRequestHandlerTarget extends ShowInviteCodeRequestHandler implements TargetEventHandler<CodeRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public ShowInviteCodeRequestHandlerTarget(@Autowired GameRoomsRegistry<?> roomRegistry,
                                              @Autowired EventDispatcher eventDispatcher) {
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

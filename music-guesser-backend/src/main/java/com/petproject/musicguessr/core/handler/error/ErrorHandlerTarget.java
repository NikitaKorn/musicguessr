package com.petproject.musicguessr.core.handler.error;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.TargetEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.response.ErrorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.petproject.musicguessr.model.EventType.ERROR;

@Component
public class ErrorHandlerTarget implements TargetEventHandler<ErrorEvent> {
    private final EventDispatcher eventDispatcher;

    public ErrorHandlerTarget(@Autowired EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(ErrorEvent event) {
        return ERROR.equals(event.getEventType());
    }

    @Override
    public void handle(ErrorEvent event, Player player) {
        eventDispatcher.sendEventToPlayers(event, Collections.singleton(player));
    }

    @Override
    public Class<ErrorEvent> getType() {
        return ErrorEvent.class;
    }
}

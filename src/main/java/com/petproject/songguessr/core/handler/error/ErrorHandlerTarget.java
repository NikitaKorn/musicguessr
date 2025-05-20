package com.petproject.songguessr.core.handler.error;

import com.petproject.songguessr.core.dispatcher.EventDispatcherImpl;
import com.petproject.songguessr.core.handler.TargetEventHandler;
import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.response.ErrorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.petproject.songguessr.model.EventType.ERROR;

@Component
public class ErrorHandlerTarget implements TargetEventHandler<ErrorEvent> {
    private final EventDispatcherImpl eventDispatcher;

    public ErrorHandlerTarget(@Autowired EventDispatcherImpl eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(BaseEvent<?> event) {
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

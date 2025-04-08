package com.petproject.musicguessr.core.processor;

import com.petproject.musicguessr.core.handler.BroadcastEventHandler;
import com.petproject.musicguessr.core.handler.EventHandler;
import com.petproject.musicguessr.core.handler.TargetEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class EventProcessorImpl<T extends BaseEvent<?>> implements EventProcessor<T> {
    private static final Logger log = LoggerFactory.getLogger(EventProcessorImpl.class);
    private final List<TargetEventHandler<T>> targetEventHandlers;
    private final List<BroadcastEventHandler<T>> broadcastEventHandlers;

    public EventProcessorImpl(
            List<TargetEventHandler<T>> targetEventHandlers,
            List<BroadcastEventHandler<T>> broadcastEventHandlers
    ) {
        this.targetEventHandlers = targetEventHandlers;
        this.broadcastEventHandlers = broadcastEventHandlers;
    }

    @Override
    public void process(T event, Player player, Set<Player> players) {
        targetEventHandlers.stream()
                .filter(handler -> canBeHandled(event, handler))
                .forEach(handler -> handler.handle(event, player));

        broadcastEventHandlers.stream()
                .filter(handler -> canBeHandled(event, handler))
                .forEach(handler -> handler.handle(event, players));
    }

    private boolean canBeHandled(T event, EventHandler<T> handler) {
        try {
            return handler.canHandle(event);
        } catch (ClassCastException e) {
            log.debug("Event {} can't be cast to {}!", event.getEventType(), handler.getType().getSimpleName());
            return false;
        }
    }
}

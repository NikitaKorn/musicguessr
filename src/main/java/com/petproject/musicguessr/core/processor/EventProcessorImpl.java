package com.petproject.musicguessr.core.processor;

import com.petproject.musicguessr.core.handler.BroadcastEventHandler;
import com.petproject.musicguessr.core.handler.TargetEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class EventProcessorImpl implements EventProcessor<BaseEvent<?>> {
    private static final Logger log = LoggerFactory.getLogger(EventProcessorImpl.class);
    private final List<TargetEventHandler<?>> targetEventHandlers;
    private final List<BroadcastEventHandler<?>> broadcastEventHandlers;

    public EventProcessorImpl(
            List<TargetEventHandler<?>> targetEventHandlers,
            List<BroadcastEventHandler<?>> broadcastEventHandlers
    ) {
        this.targetEventHandlers = targetEventHandlers;
        this.broadcastEventHandlers = broadcastEventHandlers;
    }

    @Override
    public void process(BaseEvent<?> event, Player player, Set<Player> players) {
        targetEventHandlers.forEach(handler -> handleTarget(event, player, handler));
        broadcastEventHandlers.forEach(handler -> handleBroadcast(event, players, handler));
    }

    private <T extends BaseEvent<?>> void handleTarget(
            BaseEvent<?> event,
            Player player,
            TargetEventHandler<T> handler
    ) {
        if (handler.canHandle(event)) {
            handler.handle(handler.getType().cast(event), player);
        }
    }

    private <T extends BaseEvent<?>> void handleBroadcast(
            BaseEvent<?> event,
            Set<Player> players,
            BroadcastEventHandler<T> handler
    ) {
        if (handler.canHandle(event)) {
            handler.handle(handler.getType().cast(event), players);
        }
    }
}

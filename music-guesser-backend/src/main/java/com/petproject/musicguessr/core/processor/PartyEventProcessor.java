package com.petproject.musicguessr.core.processor;

import com.petproject.musicguessr.core.handler.broadcast.BroadcastEventHandler;
import com.petproject.musicguessr.core.handler.target.TargetEventHandler;
import com.petproject.musicguessr.core.converter.MessageConverter;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.inrequest.RequestEvent;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Set;

public class PartyEventProcessor implements EventProcessor {
    private final List<TargetEventHandler> targetEventHandlers;
    private final List<BroadcastEventHandler> broadcastEventHandlers;
    private final MessageConverter messageConverter;

    public PartyEventProcessor(
            MessageConverter messageConverter,
            List<TargetEventHandler> targetEventHandlers,
            List<BroadcastEventHandler> broadcastEventHandlers
    ) {
        this.messageConverter = messageConverter;
        this.targetEventHandlers = targetEventHandlers;
        this.broadcastEventHandlers = broadcastEventHandlers;
    }

    @Override
    public void process(String message, WebSocketSession session, Set<Player> players) {
        RequestEvent event = messageConverter.parseRequestEventFromMessage(message);

        targetEventHandlers.stream()
                .filter(handler -> handler.canHandle(event))
                .forEach(handler -> handler.handle(event, session));

        broadcastEventHandlers.stream()
                .filter(handler -> handler.canHandle(event))
                .forEach(handler -> handler.handle(event, players));
    }
}

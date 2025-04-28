package com.petproject.musicguessr.core.dispatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Component
public final class EventDispatcher {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T extends BaseEvent<?>> void sendEventToPlayers(T event, Set<Player> players) {
        players.forEach(player -> {
            var session = player.getSession();
            if (player.getSession() != null && session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(event)));
                } catch (IOException e) {
                    log.error("Error while converting message to JSON! Event: {}, Error: {}", event, e.getMessage());
                }
            }
        });
    }
}

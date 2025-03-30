package com.petproject.musicguessr.core.processor;

import com.petproject.musicguessr.core.room.model.Player;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

public interface EventProcessor {
    void process(String message, WebSocketSession session, Set<Player> sessions);
}

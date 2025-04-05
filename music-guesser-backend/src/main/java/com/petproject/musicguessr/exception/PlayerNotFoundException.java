package com.petproject.musicguessr.exception;

import com.petproject.musicguessr.model.GameRoom;
import org.springframework.web.socket.WebSocketSession;

public class PlayerNotFoundException extends RuntimeException {
    private static final String PREFIX = "Player not found! ";

    public PlayerNotFoundException() {
        super(PREFIX);
    }

    public PlayerNotFoundException(String message) {
        super(PREFIX + message);
    }

    public PlayerNotFoundException(WebSocketSession session) {
        super(PREFIX + String.format("Player with session id %s not found!", session.getId()));
    }
}

package com.petproject.musicguessr.exception;

import org.springframework.web.socket.WebSocketHandler;

public class RoomNotFoundException extends RuntimeException {
    private static final String PREFIX = "Room not found for handler! ";

    public RoomNotFoundException(WebSocketHandler webSocketHandler) {
        super(PREFIX + webSocketHandler.toString());
    }

    public RoomNotFoundException() {
        super(PREFIX);
    }
}


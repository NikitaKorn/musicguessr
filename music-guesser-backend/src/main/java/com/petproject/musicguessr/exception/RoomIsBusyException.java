package com.petproject.musicguessr.exception;

import org.springframework.web.socket.WebSocketHandler;

public class RoomIsBusyException extends RuntimeException {
    private static final String PREFIX = "Room is busy! ";

    public RoomIsBusyException(WebSocketHandler webSocketHandler) {
        super(PREFIX + webSocketHandler.toString());
    }

    public RoomIsBusyException() {
        super(PREFIX);
    }
}

package com.petproject.songguessr.exception;

import com.petproject.songguessr.model.GameRoom;

public class RoomIsBusyException extends RuntimeException {
    private static final String PREFIX = "Rooms are busy! ";

    public RoomIsBusyException() {
        super(PREFIX);
    }

    public RoomIsBusyException(String message) {
        super(PREFIX + message);
    }

    public RoomIsBusyException(GameRoom room) {
        super(String.format("Room %s is busy!", room.getRoomId()));
    }
}

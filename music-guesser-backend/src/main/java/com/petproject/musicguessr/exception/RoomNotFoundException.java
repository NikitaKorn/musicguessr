package com.petproject.musicguessr.exception;

public class RoomNotFoundException extends RuntimeException {
    private static final String PREFIX = "Game room not found! ";

    public RoomNotFoundException(String message) {
        super(PREFIX + message);
    }

    public RoomNotFoundException() {
        super(PREFIX);
    }
}


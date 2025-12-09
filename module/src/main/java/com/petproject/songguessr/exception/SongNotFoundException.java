package com.petproject.songguessr.exception;

public class SongNotFoundException extends RuntimeException{
    private static final String PREFIX = "Song room not found! ";

    public SongNotFoundException(String message) {
        super(PREFIX + message);
    }

    public SongNotFoundException() {
        super(PREFIX);
    }
}

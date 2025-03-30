package com.petproject.musicguessr.model;

public enum EventType {
    SEARCH_REQUEST,
    SEARCH_RESPONSE,

    SONG_REQUEST,
    SONG_RESPONSE,

    WORD_REQUEST,
    WORD_RESPONSE,

    SHOW_INVITE_CODE_REQUEST,
    SHOW_INVITE_CODE_RESPONSE,

    NONE,
    ERROR,
}

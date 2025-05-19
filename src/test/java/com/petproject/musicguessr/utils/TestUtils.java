package com.petproject.musicguessr.utils;

import com.petproject.musicguessr.model.EventType;

public class TestUtils {
    public static String getSearchRequestTargetMessage(String payload) {
        return String.format("{\"eventType\":\"%s\",\"payload\":{\"message\":\"%s\"}}", EventType.SEARCH_REQUEST_TARGET, payload);
    }

    public static String getSongRequestTargetMessage(String payload) {
        return String.format("{\"eventType\":\"%s\",\"payload\":{\"message\":\"%s\"}}", EventType.SONG_REQUEST_TARGET, payload);
    }

    public static String getWordRequestTargetMessage() {
        return String.format("{\"eventType\":\"%s\"}", EventType.WORD_REQUEST_TARGET);
    }

    public static String getWordRequestBroadcastMessage() {
        return String.format("{\"eventType\":\"%s\"}", EventType.WORD_REQUEST_BROADCAST);
    }

    public static String getRequestMessage(EventType eventType, String payload) {
        return String.format("{\"eventType\":\"%s\",\"payload\":{\"message\":\"%s\"}}", eventType, payload);
    }

    public static String getCodeRequestTargetMessage() {
        return String.format("{\"eventType\":\"%s\"}", EventType.SHOW_INVITE_CODE_REQUEST_TARGET);
    }
}

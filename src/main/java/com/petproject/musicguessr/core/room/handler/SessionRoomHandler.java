package com.petproject.musicguessr.core.room.handler;

import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;

public interface SessionRoomHandler {
    void onConnectionOpened(Player player) throws Exception;
    void onMessageReceived(Player player, BaseEvent<?> event);
    void onConnectionClosed(Player player);
    void onTransportError(Player player, Throwable error);
}
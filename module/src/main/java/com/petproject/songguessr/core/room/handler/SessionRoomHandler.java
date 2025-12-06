package com.petproject.songguessr.core.room.handler;

import com.petproject.BaseEvent;
import com.petproject.songguessr.core.room.model.Player;

public interface SessionRoomHandler {
    void onConnectionOpened(Player player) throws Exception;
    void onMessageReceived(Player player, BaseEvent<?> event);
    void onConnectionClosed(Player player);
    void onTransportError(Player player, Throwable error);
}
package com.petproject.musicguessr.core.room.handler;

import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;

public interface SessionRoomHandler<T extends BaseEvent<?>> {
    void onConnectionOpened(Player player) throws Exception;
    void onMessageReceived(Player player, T event);
    void onConnectionClosed(Player player);
    void onTransportError(Player player, Throwable error);
}

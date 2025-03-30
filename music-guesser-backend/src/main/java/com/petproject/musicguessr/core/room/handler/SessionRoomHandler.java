package com.petproject.musicguessr.core.room.handler;

import com.petproject.musicguessr.core.room.model.Player;
import org.springframework.web.socket.CloseStatus;

public interface SessionRoomHandler {
    void onConnectionOpened(Player player) throws Exception;
    void onMessageReceived(Player player, String message);
    void onConnectionClosed(Player player, CloseStatus status);
    void onTransportError(Player player, Throwable error);
}

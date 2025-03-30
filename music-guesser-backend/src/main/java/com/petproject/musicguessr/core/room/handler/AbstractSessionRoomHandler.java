package com.petproject.musicguessr.core.room.handler;

import com.petproject.musicguessr.config.GameSessionRoomRegistry;
import com.petproject.musicguessr.core.processor.EventProcessor;
import com.petproject.musicguessr.core.room.model.Player;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
public abstract class AbstractSessionRoomHandler implements SessionRoomHandler {
    @Getter
    protected final String roomId;
    protected final GameSessionRoomRegistry roomRegistry;
    protected final EventProcessor eventProcessor;
    @Getter
    protected final Set<Player> players = new HashSet<>();
//    protected final Map<WebSocketSession, Player> players = new ConcurrentHashMap<>(); // ToDo избавиться от спринговой зависимости WebSocketSession

    public AbstractSessionRoomHandler(
            GameSessionRoomRegistry roomRegistry,
            EventProcessor eventProcessor,
            String roomIdPrefix
    ) {
        this.roomRegistry = roomRegistry;
        this.eventProcessor = eventProcessor;
        this.roomId = "%s%s".formatted(roomIdPrefix, UUID.randomUUID());
    }

    @Override
    public abstract void onConnectionOpened(Player player) throws Exception;

    @Override
    public void onMessageReceived(Player player, String message) {
        roomRegistry.refreshLastEventTime(roomId);
        eventProcessor.process(message, player.getSession(), players);
    }

    @Override
    public abstract void onConnectionClosed(Player player, CloseStatus status);

    @Override
    public void onTransportError(Player player, Throwable error) {
        log.error("Transport error for client {}: {}", player.getName(), error.getMessage());
    }

    public abstract String getInviteCode();

    protected void closeSession(WebSocketSession session) {
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("Error while closing session: {}", e.getMessage());
            }
        }
    }

    public void closeRoom() {
        players.forEach((player -> closeSession(player.getSession())));
    }
}

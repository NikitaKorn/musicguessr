package com.petproject.musicguessr.core.room.adapter;

import com.petproject.musicguessr.core.room.handler.SessionRoomHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.service.word.RandomPlayersNameService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Scope("prototype")
public class WebSocketHandlerAdapter implements WebSocketHandler {
    private final SessionRoomHandler gameSessionHandler;
    @Autowired
    private RandomPlayersNameService playersNameService;
    private final Map<WebSocketSession, Player> players;

    public WebSocketHandlerAdapter(SessionRoomHandler gameSessionHandler) {
        this.players = new ConcurrentHashMap<>();
        this.gameSessionHandler = gameSessionHandler;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        Player client = players.computeIfAbsent(session, (ss) -> new Player(ss, playersNameService.peekRandom()));
        gameSessionHandler.onConnectionOpened(client);
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) {
        Player client = Optional.of(players.get(session)).orElseThrow(RuntimeException::new);  // ToDo refactor to custom ex
        if (message instanceof TextMessage textMessage) {
            gameSessionHandler.onMessageReceived(client, textMessage.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        Player client = players.remove(session);
        if (client != null) {
            gameSessionHandler.onConnectionClosed(client, closeStatus);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        Player client = players.get(session);
        if (client != null) {
            gameSessionHandler.onTransportError(client, exception);
        }
    }

    /**
     * Отключает поддержку частичных сообщений.
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

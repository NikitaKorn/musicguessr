package com.petproject.musicguessr.core.room.adapter;

import com.petproject.musicguessr.core.converter.MessageConverter;
import com.petproject.musicguessr.core.room.handler.SessionRoomHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
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

import static com.petproject.musicguessr.utils.GameRoomsUtils.playerNotFoundEx;

/**
 * Адаптер для обработки WebSocket соединений, делегирующий логику управления игровыми сессиями
 * компоненту {@link SessionRoomHandler}.
 * Адаптер инкапслирует логику обработки сессий и скрывает фактическую реализацию. При таком подходе,
 * классы с бизнес логикой не завязываются на определенной технологии (websocket, rest и т.д).
 * Эту функцию берет на себя класс адаптер.
 *
 * <p>
 * Класс выполняет следующие задачи:
 * <ul>
 *     <li>Регистрирует новых игроков при установке соединения, используя {@link RandomPlayersNameService} для генерации имен.</li>
 *     <li>Перенаправляет входящие сообщения в {@link SessionRoomHandler} для обработки игровой логики.</li>
 *     <li>Управляет жизненным циклом соединений, уведомляя {@link SessionRoomHandler} о событиях открытия/закрытия.</li>
 *     <li>Обрабатывает ошибки транспорта, связанные с WebSocket-сессиями.</li>
 * </ul>
 *
 * Использует потокобезопасную {@link ConcurrentHashMap} для хранения соответствия между
 * сессиями ({@link WebSocketSession}) и игроками ({@link Player}).
 *
 * @see WebSocketHandler
 * @see SessionRoomHandler
 */
@Slf4j
@Component
@Scope("prototype")
public class WebSocketHandlerAdapter<T extends BaseEvent<?>> implements WebSocketHandler {
    @Autowired
    private RandomPlayersNameService playersNameService;
    @Autowired
    private MessageConverter<T> messageConverter;
    private final SessionRoomHandler<T> gameSessionHandler;
    private final Map<WebSocketSession, Player> players;

    public WebSocketHandlerAdapter(SessionRoomHandler<T> gameSessionHandler) {
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
        Player client = getPlayerOrThrowEx(session);
        if (message instanceof TextMessage textMessage) {
            T event = messageConverter.parseRequestEventFromMessage(textMessage.getPayload());
            gameSessionHandler.onMessageReceived(client, event);
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) {
        Player client = removePlayerOrThrowEx(session);
        gameSessionHandler.onConnectionClosed(client);
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
        Player client = getPlayerOrThrowEx(session);
        gameSessionHandler.onTransportError(client, exception);
    }

    /**
     * Отключает поддержку частичных сообщений.
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private Player getPlayerOrThrowEx(WebSocketSession session) {
        return Optional
                .of(players.get(session))
                .orElseThrow(playerNotFoundEx(session));
    }

    private Player removePlayerOrThrowEx(WebSocketSession session) {
        return Optional
                .of(players.remove(session))
                .orElseThrow(playerNotFoundEx(session));
    }
}
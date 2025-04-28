package com.petproject.musicguessr.core.room.handler;

import com.petproject.musicguessr.core.processor.EventProcessor;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.service.registry.GameRoomsRegistry;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Абстрактный базовый класс для обработки игровых сессий в комнатах. Предоставляет общую логику
 * управления подключениями игроков, обработки сообщений и закрытия комнат. Реализации должны
 * определить специфичное поведение для открытия/закрытия соединений и генерации кодов приглашения.
 *
 * <p><b>Основные обязанности:</b>
 * <ul>
 *   <li>Управление набором игроков ({@link #players}) в потокобезопасном режиме.</li>
 *   <li>Обновление времени последней активности комнаты через {@link GameRoomsRegistry}.</li>
 *   <li>Обработка транспортных ошибок и закрытие сессий игроков.</li>
 *   <li>Предоставление шаблонных методов для реализации в подклассах.</li>
 * </ul>
 *
 * <p><b>Жизненный цикл:</b>
 * <ol>
 *   <li>При создании генерируется уникальный ID комнаты на основе {@code roomIdPrefix} и UUID.</li>
 *   <li>Все входящие сообщения триггерят обновление времени последней активности.</li>
 *   <li>При закрытии комнаты все сессии игроков безопасно закрываются.</li>
 * </ol>
 *
 * @see SessionRoomHandler
 * @see GameRoomsRegistry
 * @see EventProcessor
 */
@Slf4j
public abstract class AbstractSessionRoomHandler implements SessionRoomHandler {
    @Getter
    protected final String roomId;
    protected final GameRoomsRegistry<?> roomRegistry;
    protected final EventProcessor<BaseEvent<?>> eventProcessor;
    @Getter
    protected final Set<Player> players = ConcurrentHashMap.newKeySet();

    public AbstractSessionRoomHandler(
            GameRoomsRegistry<?> roomRegistry,
            EventProcessor<BaseEvent<?>> eventProcessor,
            String roomIdPrefix
    ) {
        this.roomRegistry = roomRegistry;
        this.eventProcessor = eventProcessor;
        this.roomId = "%s%s".formatted(roomIdPrefix, UUID.randomUUID());
    }

    @Override
    public abstract void onConnectionOpened(Player player) throws Exception;

    @Override
    public void onMessageReceived(Player player, BaseEvent<?> event) {
        eventProcessor.process(event, player, players);
    }

    @Override
    public abstract void onConnectionClosed(Player player);

    @Override
    public void onTransportError(Player player, Throwable error) {
        log.error("Transport error for client {}: {}", player.getName(), error.getMessage());
    }

    protected void closePlayerSession(Player player) {
        var session = player.getSession();
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("Error while closing session: {}", e.getMessage());
            }
        }
    }

    public abstract String getInviteCode();

    public void closeRoom() {
        // new ArrayList allows to avoid ConcurrentModificationException
        new ArrayList<>(players).forEach(this::closePlayerSession);
    }
}

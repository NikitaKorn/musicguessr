package com.petproject.musicguessr.service.registry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
import com.petproject.musicguessr.model.GameRoom;

import static com.petproject.musicguessr.utils.AppUtils.getCurrentTimeInSeconds;

/**
 * Служба для автоматического закрытия неактивных игровых комнат на основе тайм-аута бездействия.
 * Периодически проверяет все зарегистрированные комнаты и закрывает те, где не было активности
 * дольше заданного времени.
 *
 * <p><b>Логика работы:</b>
 * <ol>
 *   <li>Получает текущее время в секундах.</li>
 *   <li>Фильтрует комнаты:
 *     <ul>
 *       <li>Только занятые комнаты ({@link GameRoom#isBusy()}).</li>
 *       <li>Время с последнего события превышает порог ({@link GameRoom#getLastEventTime()}).</li>
 *     </ul>
 *   </li>
 *   <li>Для каждой подходящей комнаты:
 *     <ul>
 *       <li>Вызывает {@link AbstractSessionRoomHandler#closeRoom()}.</li>
 *       <li>Логирует закрытие с указанием ID комнаты и времени бездействия.</li>
 *     </ul>
 *   </li>
 * </ol>
 *
 * <p><b>Потокобезопасность:</b><br>
 * Не гарантируется. Предполагается, что доступ к {@link GameRoomsRegistry} синхронизирован на уровне самого реестра.
 *
 * @see GameRoomsRegistry
 * @see AbstractSessionRoomHandler
 */
@Slf4j
@Service
public final class CloseInactiveSessionsScheduler {
    private final GameRoomsRegistry roomsRegistry;
    private final float inactivationTime;

    public CloseInactiveSessionsScheduler(
            @Value("${service.configuration.session-room-registry.inactivation-time}") float inactivationTime,
            @Autowired GameRoomsRegistry roomsRegistry
    ) {
        this.inactivationTime = inactivationTime * 60; // convert to seconds
        this.roomsRegistry = roomsRegistry;
    }

    @Scheduled(fixedDelayString = "${service.configuration.session-room-registry.scheduler.delay}000")
    private void closeInactiveSessions() {
        long currentTime = getCurrentTimeInSeconds();
        roomsRegistry.getGameRooms().values().stream()
                .filter(room -> {
                    long diff = currentTime - room.getLastEventTime();
                    log.debug("Last event in game session {} was {} seconds ago", room.getRoomId(), diff);
                    return (room.isBusy()) && (diff > inactivationTime);
                })
                .forEach(room -> {
                    room.getRoom().closeRoom();
                    log.info("Game session in {} was closed due to inactivation after {} seconds", room.getRoomId(), inactivationTime);
                });
    }
}

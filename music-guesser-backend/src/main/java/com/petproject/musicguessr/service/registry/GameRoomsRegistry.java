package com.petproject.musicguessr.service.registry;

import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
import com.petproject.musicguessr.exception.RoomIsBusyException;
import com.petproject.musicguessr.exception.RoomNotFoundException;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.GameRoom;
import com.petproject.musicguessr.utils.GameRoomsUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

import static com.petproject.musicguessr.utils.GameRoomsUtils.*;
import static com.petproject.musicguessr.utils.AppUtils.getCurrentTimeInSeconds;

/**
 * Сервис для управления игровыми комнатами. Предоставляет методы для поиска, создания,
 * бронирования и освобождения комнат, а также отслеживания их состояния.
 *
 * <p>Класс параметризован типом {@code T}, который определяет обработчик сессий для комнаты.
 * Каждая комната ({@link GameRoom}) связана с конкретным обработчиком {@link AbstractSessionRoomHandler}.
 *
 * <p><b>Потокобезопасность:</b>
 * <ul>
 *   <li>Методы {@link #tryToBookRoom(String)} и {@link #tryToReleaseRoom(String)} синхронизированы
 *       для атомарного изменения состояния комнаты.</li>
 *   <li>Остальные методы не гарантируют потокобезопасность при конкурентном доступе.</li>
 *   <li>Внутреннее хранилище комнат ({@code gameRooms}) использует {@link HashMap}, поэтому
 *       в многопоточной среде рекомендуется внешняя синхронизация.</li>
 * </ul>
 *
 * @param <T> тип обработчика сессии комнаты, наследующий {@link AbstractSessionRoomHandler}.
 *
 * @see GameRoom
 * @see AbstractSessionRoomHandler
 * @see RoomIsBusyException
 * @see RoomNotFoundException
 */
@Slf4j
@Getter
@Service
public final class GameRoomsRegistry<T extends AbstractSessionRoomHandler<BaseEvent<?>>> {
    private final Map<String, GameRoom<T>> gameRooms = new HashMap<>();

    public GameRoom<T> findFreeSoloGameRoom() {
        return gameRooms.values().stream()
                .filter(isSoloGameRoom())
                .filter(GameRoomsUtils::isGameRoomFree)
                .findFirst()
                .orElseThrow(roomIsBusyEx("All solo rooms are busy!"));
    }

    public GameRoom<T> findFreePartyGameRoom() {
        return gameRooms.values().stream()
                .filter(isPartyGameRoom())
                .filter(GameRoomsUtils::isGameRoomFree)
                .findFirst()
                .orElseThrow(roomIsBusyEx("All party rooms are busy!"));
    }

    public GameRoom<T> findGameRoomByInviteCode(String inviteCode) {
        return gameRooms.values().stream()
                .filter(isPartyGameRoom())
                .filter(hasRoomWithInviteCode(inviteCode))
                .findFirst()
                .orElseThrow(roomNotFoundEx(String.format("Rooms with invite code %s not found!", inviteCode)));
    }

    public GameRoom<T> findRoomByPlayerSession(WebSocketSession session) {
        return gameRooms.values().stream()
                .filter(hasPlayerSessionInGameRoom(session))
                .findFirst()
                .orElseThrow(roomNotFoundEx(String.format("Rooms with player session %s not found!", session.getId())));
    }

    public void addRoom(String roomId, GameRoom<T> gameSessionModel) {
        gameRooms.putIfAbsent(roomId, gameSessionModel);
    }

    public synchronized void tryToBookRoom(String roomId) {
        var room = getRoomByIdOrThrowException(gameRooms, roomId);
        if (room.isBusy()) {
            throw new RoomIsBusyException(room);
        } else {
            room.setBusy(true);
            log.info("{} was booked", room.getRoomId());
        }
    }

    public synchronized void tryToReleaseRoom(String roomId) {
        var room = getRoomByIdOrThrowException(gameRooms, roomId);
        room.setBusy(false);
        log.info("{} was released", room.getRoomId());
    }

    public void refreshLastEventTimeInRoom(String roomId) {
        var room = getRoomByIdOrThrowException(gameRooms, roomId);
        room.setLastEventTime(getCurrentTimeInSeconds());
    }

    public int getRoomsCount() {
        return gameRooms.size();
    }
}
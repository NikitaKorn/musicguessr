package com.petproject.musicguessr.service.registry;

import com.petproject.musicguessr.exception.RoomIsBusyException;
import com.petproject.musicguessr.model.GameRoom;
import com.petproject.musicguessr.utils.GameRoomsUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

import static com.petproject.musicguessr.utils.AppUtils.getCurrentTimeInSeconds;
import static com.petproject.musicguessr.utils.GameRoomsUtils.*;

@Slf4j
@Getter
@Service
public final class GameRoomsRegistry {
    private final Map<String, GameRoom> gameRooms = new HashMap<>();

    public GameRoom findFreeSoloGameRoom() {
        return gameRooms.values().stream()
                .filter(GameRoomsUtils::isSoloGameRoom)
                .filter(GameRoomsUtils::isGameRoomFree)
                .findFirst()
                .orElseThrow(roomIsBusyEx("All solo rooms are busy!"));
    }

    public GameRoom findFreePartyGameRoom() {
        return gameRooms.values().stream()
                .filter(GameRoomsUtils::isPartyGameRoom)
                .filter(GameRoomsUtils::isGameRoomFree)
                .findFirst()
                .orElseThrow(roomIsBusyEx("All party rooms are busy!"));
    }

    public GameRoom findGameRoomByInviteCode(String inviteCode) {
        return gameRooms.values().stream()
                .filter(GameRoomsUtils::isPartyGameRoom)
                .filter(hasRoomWithInviteCode(inviteCode))
                .findFirst()
                .orElseThrow(roomNotFoundEx(String.format("Rooms with invite code %s not found!", inviteCode)));
    }

    public GameRoom findRoomByPlayerSession(WebSocketSession session) {
        return gameRooms.values().stream()
                .filter(doesPlayerHasOpenSession(session))
                .findFirst()
                .orElseThrow(roomNotFoundEx(String.format("Rooms with player session %s not found!", session.getId())));
    }

    public void addRoom(String roomId, GameRoom gameSessionModel) {
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
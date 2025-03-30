package com.petproject.musicguessr.config;

import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.exception.RoomIsBusyException;
import com.petproject.musicguessr.exception.RoomNotFoundException;
import com.petproject.musicguessr.model.GameSessionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.petproject.musicguessr.utils.AppUtils.getCurrentTimeInSeconds;

@Slf4j
@Service
public class GameSessionRoomRegistry {
    private final Map<String, GameSessionModel<? extends AbstractSessionRoomHandler>> sessionRooms;
    private final float inactivationTime;

    public GameSessionRoomRegistry(@Value("${service.configuration.session-room-registry.inactivation-time}") float inactivationTime) {
        sessionRooms = new ConcurrentHashMap<>();
        this.inactivationTime = inactivationTime * 60; // convert to minutes
    }

    public synchronized GameSessionModel<? extends AbstractSessionRoomHandler> findSoloFreeRoom() {
        return sessionRooms.values().stream()
                .filter(room -> room.getType() == GameSessionModel.Type.SOLO)
                .filter(room -> !room.isBusy())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("All solo rooms are busy!"));
    }

    public synchronized GameSessionModel<? extends AbstractSessionRoomHandler> findPartyFreeRoom() {
        return sessionRooms.values().stream()
                .filter(room -> room.getType() == GameSessionModel.Type.PARTY)  //ToDo вынести проверку в отдельный метод в утилитарный класс
                .filter(room -> !room.isBusy())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("All party rooms are busy!"));
    }

    public synchronized GameSessionModel<? extends AbstractSessionRoomHandler> findRoomOnInviteCode(String clientInviteCode) {
        return sessionRooms.values().stream()
                .filter(room -> room.getType() == GameSessionModel.Type.PARTY)
                .filter(room -> clientInviteCode.equals(room.getRoom().getInviteCode()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Rooms with invite code %s doesn't exist!", clientInviteCode)));
    }

    public synchronized AbstractSessionRoomHandler findRoomBySession(WebSocketSession session) {
        return sessionRooms.values().stream()
                .map(GameSessionModel::getRoom)
                .filter(room -> {
                    Set<Player> players = room.getPlayers();
                    return players.stream().anyMatch(player -> player.getSession().equals(session));  //ToDo вынести в отдельный метод
                })
                .findFirst().orElseThrow(RoomNotFoundException::new);  // ToDo Да и в целом тут какая то хуйня
    }

    public void addGameSessionRoom(String roomId, GameSessionModel<? extends AbstractSessionRoomHandler> gameSessionModel) {
        sessionRooms.putIfAbsent(roomId, gameSessionModel);
    }

    public synchronized void tryToBookRoom(String roomId) {
        var room = getRoomOrThrowException(roomId);
        if (room.isBusy()) {
            throw new RoomIsBusyException();
        } else {
            room.setBusy(true);
            log.info("{} was booked", room.getRoomId());
        }
    }

    public synchronized void tryToReleaseRoom(String roomId) {
        var room = getRoomOrThrowException(roomId);
        room.setBusy(false);
        log.info("{} was released", room.getRoomId());
    }

    private GameSessionModel<?> getRoomOrThrowException(String roomId) {
        return Optional.ofNullable(sessionRooms.get(roomId))
                .orElseThrow(RoomNotFoundException::new);
    }

    public void refreshLastEventTime(String roomId) {
        Optional.ofNullable(sessionRooms.get(roomId))
                .orElseThrow(RoomNotFoundException::new)
                .setLastEventTime(getCurrentTimeInSeconds());
    }

    @Scheduled(fixedDelayString = "${service.configuration.session-room-registry.scheduler.delay}000")
    private void closeInactiveSessions() {
        long currentTime = getCurrentTimeInSeconds();
        sessionRooms.values().stream()
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

    public int getRoomsCount() {
        return sessionRooms.size();
    }
}

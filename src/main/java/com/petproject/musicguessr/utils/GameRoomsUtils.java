package com.petproject.musicguessr.utils;

import com.petproject.musicguessr.exception.PlayerNotFoundException;
import com.petproject.musicguessr.exception.RoomIsBusyException;
import com.petproject.musicguessr.exception.RoomNotFoundException;
import com.petproject.musicguessr.model.GameRoom;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class GameRoomsUtils {
    public static boolean isSoloGameRoom(GameRoom room) {
        return room.getType() == GameRoom.Type.SOLO;
    }

    public static boolean isPartyGameRoom(GameRoom room) {
        return room.getType() == GameRoom.Type.PARTY;
    }

    public static boolean isGameRoomBusy(GameRoom room) {
        return room.isBusy();
    }

    public static boolean isGameRoomFree(GameRoom room) {
        return !isGameRoomBusy(room);
    }

    public static Predicate<GameRoom> doesPlayerHasOpenSession(WebSocketSession session) {
        return room -> room.getRoom().getPlayers().stream().anyMatch(player -> player.getSession().equals(session));
    }

    public static Predicate<GameRoom> hasRoomWithInviteCode(String inviteCode) {
        return room -> inviteCode.equals(room.getRoom().getInviteCode());
    }

    public static GameRoom getRoomByIdOrThrowException(Map<String, GameRoom> gameRooms, String roomId) {
        return Optional.ofNullable(gameRooms.get(roomId))
                .orElseThrow(() -> new RoomNotFoundException(String.format("Rooms with id %s doesn't exist!", roomId)));
    }

    public static Supplier<RoomNotFoundException> roomNotFoundEx(String message) {
        return () -> new RoomNotFoundException(message);
    }

    public static Supplier<PlayerNotFoundException> playerNotFoundEx(String message) {
        return () -> new PlayerNotFoundException(message);
    }

    public static Supplier<PlayerNotFoundException> playerNotFoundEx(WebSocketSession session) {
        return () -> new PlayerNotFoundException(session);
    }

    public static Supplier<RoomIsBusyException> roomIsBusyEx(String message) {
        return () -> new RoomIsBusyException(message);
    }
}

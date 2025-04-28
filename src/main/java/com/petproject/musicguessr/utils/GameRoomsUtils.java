package com.petproject.musicguessr.utils;

import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
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
    public static <T extends AbstractSessionRoomHandler> boolean isSoloGameRoom(GameRoom<T> room) {
        return room.getType() == GameRoom.Type.SOLO;
    }

    public static <T extends AbstractSessionRoomHandler> boolean isPartyGameRoom(GameRoom<T> room) {
        return room.getType() == GameRoom.Type.PARTY;
    }

    public static <T extends AbstractSessionRoomHandler> boolean isGameRoomBusy(GameRoom<T> room) {
        return room.isBusy();
    }

    public static <T extends AbstractSessionRoomHandler> boolean isGameRoomFree(GameRoom<T> room) {
        return !isGameRoomBusy(room);
    }

    public static <T extends AbstractSessionRoomHandler> Predicate<GameRoom<T>> doesPlayerHasOpenSession(WebSocketSession session) {
        return room -> room.getRoom().getPlayers().stream().anyMatch(player -> player.getSession().equals(session));
    }

    public static <T extends AbstractSessionRoomHandler> Predicate<GameRoom<T>> hasRoomWithInviteCode(String inviteCode) {
        return room -> inviteCode.equals(room.getRoom().getInviteCode());
    }

    public static <T extends AbstractSessionRoomHandler> GameRoom<T> getRoomByIdOrThrowException(Map<String, GameRoom<T>> gameRooms, String roomId) {
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

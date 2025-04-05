package com.petproject.musicguessr.model;

import com.petproject.musicguessr.core.room.adapter.WebSocketHandlerAdapter;
import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static com.petproject.musicguessr.utils.AppUtils.getCurrentTimeInSeconds;

@Getter
@Setter
public class GameRoom<T extends AbstractSessionRoomHandler>{
    private final String roomId;
    private final WebSocketHandlerAdapter adapter;
    private final T room;
    private final String path;
    private boolean isBusy;
    private final Type type;
    private long lastEventTime;

    private GameRoom(Builder<T> builder) {
        this.roomId = builder.roomId;
        this.lastEventTime = getCurrentTimeInSeconds();
        this.path = UUID.randomUUID().toString();

        this.room = builder.room;
        this.adapter = builder.adapter;
        this.isBusy = builder.busy;
        this.type = builder.type;
    }

    public static <T extends AbstractSessionRoomHandler> Builder<T> builder() {
        return new Builder<>();
    }

    public enum Type {
        SOLO,
        PARTY;
    }

    public static class Builder<T extends AbstractSessionRoomHandler> {
        private String roomId;
        private T room;
        private WebSocketHandlerAdapter adapter;
        private String path;
        private boolean busy;
        private Type type;

        public Builder<T> roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder<T> handler(T handler) {
            this.room = handler;
            return this;
        }

        public Builder<T> adapter(WebSocketHandlerAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder<T> isBusy(boolean busy) {
            this.busy = busy;
            return this;
        }

        public Builder<T> type(Type type){
            this.type = type;
            return this;
        }

        public GameRoom<T> build() {
            if (room == null) {
                throw new IllegalStateException("Room and path are required");
            }
            return new GameRoom<>(this);
        }
    }
}

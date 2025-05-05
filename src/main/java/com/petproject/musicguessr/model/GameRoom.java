package com.petproject.musicguessr.model;

import com.petproject.musicguessr.core.room.adapter.WebSocketHandlerAdapter;
import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static com.petproject.musicguessr.utils.AppUtils.getCurrentTimeInSeconds;

@Getter
@Setter
public class GameRoom {
    private final String roomId;
    private final WebSocketHandlerAdapter adapter;
    private final AbstractSessionRoomHandler room;
    private final String path;
    private boolean isBusy;
    private final Type type;
    private long lastEventTime;

    private GameRoom(Builder builder) {
        this.roomId = builder.roomId;
        this.lastEventTime = getCurrentTimeInSeconds();
        this.path = UUID.randomUUID().toString();

        this.room = builder.room;
        this.adapter = builder.adapter;
        this.isBusy = builder.busy;
        this.type = builder.type;
    }

    public static <T extends AbstractSessionRoomHandler> Builder builder() {
        return new Builder();
    }

    public enum Type {
        SOLO,
        PARTY;
    }

    public static class Builder {
        private String roomId;
        private AbstractSessionRoomHandler room;
        private WebSocketHandlerAdapter adapter;
        private String path;
        private boolean busy;
        private Type type;

        public Builder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder handler(AbstractSessionRoomHandler handler) {
            this.room = handler;
            return this;
        }

        public Builder adapter(WebSocketHandlerAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder isBusy(boolean busy) {
            this.busy = busy;
            return this;
        }

        public Builder type(Type type){
            this.type = type;
            return this;
        }

        public GameRoom build() {
            if (room == null) {
                throw new IllegalStateException("Room and path are required");
            }
            return new GameRoom(this);
        }
    }
}

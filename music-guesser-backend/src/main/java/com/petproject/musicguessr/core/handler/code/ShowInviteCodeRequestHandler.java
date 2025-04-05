package com.petproject.musicguessr.core.handler.code;

import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.service.registry.GameRoomsRegistry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowInviteCodeRequestHandler {
    protected final GameRoomsRegistry<?> roomRegistry;
}

package com.petproject.musicguessr.core.room.handler.impl;

import com.petproject.musicguessr.core.processor.EventProcessor;
import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.response.ErrorEvent;
import com.petproject.musicguessr.service.registry.GameRoomsRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@Scope("prototype")
public class SoloSessionRoomHandler extends AbstractSessionRoomHandler {

    public SoloSessionRoomHandler(GameRoomsRegistry roomRegistry, EventProcessor<BaseEvent<?>> eventProcessor) {
        super(roomRegistry, eventProcessor);
    }

    @Override
    public void onConnectionOpened(Player player) {
        if (!CollectionUtils.isEmpty(this.players) && !getSingleSession().equals(player)) {
            log.warn("Player with session {} try to connect to busy room", player.getSession().getId());
            eventProcessor.process(new ErrorEvent("Room is busy!"), player, players);
            closePlayerSession(player);
            roomRegistry.tryToReleaseRoom(roomId);
        }
        roomRegistry.tryToBookRoom(roomId);
        roomRegistry.refreshLastEventTimeInRoom(roomId);
        players.add(player);
    }

    @Override
    public void onConnectionClosed(Player player) {
        roomRegistry.tryToReleaseRoom(roomId);
        this.players.clear();
    }

    @Override
    public String getInviteCode() {
        return "";
    }

    private Player getSingleSession() {
        if (players.size() > 1) {
            throw new IllegalStateException("Solo room cannot have multiple players");
        }
        return players.iterator().next();
    }

    @Override
    protected String getPrefixId() {
        return "SoloSessionRoom";
    }
}

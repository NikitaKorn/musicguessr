package com.petproject.musicguessr.core.room.handler.impl;

import com.petproject.musicguessr.core.processor.EventProcessor;
import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.service.code.InviteCodeService;
import com.petproject.musicguessr.service.registry.GameRoomsRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@Scope("prototype")
public class PartySessionRoomHandler extends AbstractSessionRoomHandler {
    private final InviteCodeService inviteCodeService;

    public PartySessionRoomHandler(
            GameRoomsRegistry roomRegistry,
            EventProcessor<BaseEvent<?>> eventProcessor,
            InviteCodeService inviteCodeService
    ) {
        super(roomRegistry, eventProcessor);
        this.inviteCodeService = inviteCodeService;
    }

    @Override
    public void onConnectionOpened(Player player) {
        if (players.isEmpty()) {
            roomRegistry.tryToBookRoom(roomId);
            inviteCodeService.generateInviteCode();
        }
        players.add(player);
    }

    @Override
    public void onConnectionClosed(Player player) {
        players.remove(player);
        if (CollectionUtils.isEmpty(players)) {
            roomRegistry.tryToReleaseRoom(roomId);
        }
    }

    @Override
    public String getInviteCode() {
        return inviteCodeService.getInviteCode();
    }

    @Override
    protected String getPrefixId() {
        return "PartySession";
    }
}

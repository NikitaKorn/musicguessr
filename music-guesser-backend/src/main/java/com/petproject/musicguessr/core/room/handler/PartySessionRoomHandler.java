package com.petproject.musicguessr.core.room.handler;

import com.petproject.musicguessr.config.GameSessionRoomRegistry;
import com.petproject.musicguessr.core.processor.EventProcessor;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.service.code.InviteCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;

@Slf4j
@Component
@Scope("prototype")
public class PartySessionRoomHandler extends AbstractSessionRoomHandler {
    private final InviteCodeService inviteCodeService;
    private static final String PREFIX = "PartySessionRoom-";

    public PartySessionRoomHandler(GameSessionRoomRegistry roomRegistry,
                                   @Qualifier("partyEventProcessor") EventProcessor eventProcessor,
                                   InviteCodeService inviteCodeService
    ) {
        super(roomRegistry, eventProcessor, PREFIX);
        this.inviteCodeService = inviteCodeService;
    }

    @Override
    public void onConnectionOpened(Player player) {
        if (this.players.isEmpty()) {
            roomRegistry.tryToBookRoom(roomId);
            inviteCodeService.generateInviteCode();
        }
        roomRegistry.refreshLastEventTime(roomId);
        players.add(player);
    }

    @Override
    public void onConnectionClosed(Player player, CloseStatus status) {
        players.remove(player);
        if (CollectionUtils.isEmpty(players)) {
            roomRegistry.tryToReleaseRoom(roomId);
        }
    }

    @Override
    public String getInviteCode() {
        return inviteCodeService.getInviteCode();
    }
}

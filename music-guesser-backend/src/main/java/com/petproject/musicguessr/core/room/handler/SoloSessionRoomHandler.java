package com.petproject.musicguessr.core.room.handler;

import com.petproject.musicguessr.config.GameSessionRoomRegistry;
import com.petproject.musicguessr.core.processor.EventProcessor;
import com.petproject.musicguessr.core.room.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

@Slf4j
@Component
@Scope("prototype")
public class SoloSessionRoomHandler extends AbstractSessionRoomHandler {
    private static final String PREFIX = "SoloSessionRoom-";

    public SoloSessionRoomHandler(GameSessionRoomRegistry roomRegistry,
                                  @Qualifier("soloEventProcessor") EventProcessor eventProcessor) {
        super(roomRegistry, eventProcessor, PREFIX);
    }

    @Override
    public void onConnectionOpened(Player player) throws Exception {
        if (!CollectionUtils.isEmpty(this.players) && !getSingleSession().equals(player.getSession())) {
            log.warn("Player with session {} try to connect to busy room", player.getSession().getId());
            player.getSession().sendMessage(new TextMessage("Room is busy!")); // ToDo создать ивент занятой комнаты
            closeSession(player.getSession());
            roomRegistry.tryToReleaseRoom(roomId);
        }
        roomRegistry.tryToBookRoom(roomId);
        roomRegistry.refreshLastEventTime(roomId);
        players.add(player);
    }

    @Override
    public void onConnectionClosed(Player player, CloseStatus status) {
        roomRegistry.tryToReleaseRoom(roomId);
        this.players.clear();
    }

    @Override
    public String getInviteCode() {
        return "";
    }

    private Player getSingleSession(){
        if(players.size() > 1){
            throw new RuntimeException("More then 1 user in solo room!");
        }
        return players.iterator().next();
    }
}

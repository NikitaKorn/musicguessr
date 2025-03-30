package com.petproject.musicguessr.core.handler.broadcast;

import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.inrequest.RequestEvent;

import java.util.Set;

public interface BroadcastEventHandler {
    boolean canHandle(RequestEvent event);
    void handle(RequestEvent event, Set<Player> players);
}

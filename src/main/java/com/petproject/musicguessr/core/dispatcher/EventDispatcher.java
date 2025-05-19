package com.petproject.musicguessr.core.dispatcher;

import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;

import java.util.Set;

public interface EventDispatcher {
    <T extends BaseEvent<?>> void sendEventToPlayers(T event, Set<Player> players);
}

package com.petproject.songguessr.core.dispatcher;

import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.model.BaseEvent;

import java.util.Set;

public interface EventDispatcher {
    <T extends BaseEvent<?>> void sendEventToPlayers(T event, Set<Player> players);
}

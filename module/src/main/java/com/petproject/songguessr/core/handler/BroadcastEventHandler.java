package com.petproject.songguessr.core.handler;

import com.petproject.BaseEvent;
import com.petproject.songguessr.core.room.model.Player;

import java.util.Set;

public interface BroadcastEventHandler<T extends BaseEvent<?>> extends EventHandler<T> {
    void handle(T event, Set<Player> players);
}

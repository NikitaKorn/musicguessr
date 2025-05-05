package com.petproject.musicguessr.core.handler;

import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;

import java.util.Set;

public interface BroadcastEventHandler<T extends BaseEvent<?>> extends EventHandler<T> {
    void handle(T event, Set<Player> players);
}

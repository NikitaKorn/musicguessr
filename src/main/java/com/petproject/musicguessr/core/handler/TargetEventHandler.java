package com.petproject.musicguessr.core.handler;

import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;

public interface TargetEventHandler<T extends BaseEvent<?>> extends EventHandler<T> {
    void handle(T event, Player player);
}

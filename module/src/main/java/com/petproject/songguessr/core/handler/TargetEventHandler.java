package com.petproject.songguessr.core.handler;

import com.petproject.songguessr.core.room.model.Player;
import com.petproject.BaseEvent;

public interface TargetEventHandler<T extends BaseEvent<?>> extends EventHandler<T> {
    void handle(T event, Player player);
}

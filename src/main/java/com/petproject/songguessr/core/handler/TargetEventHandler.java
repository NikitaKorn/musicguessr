package com.petproject.songguessr.core.handler;

import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.model.BaseEvent;

public interface TargetEventHandler<T extends BaseEvent<?>> extends EventHandler<T> {
    void handle(T event, Player player);
}

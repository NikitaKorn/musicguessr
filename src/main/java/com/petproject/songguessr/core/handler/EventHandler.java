package com.petproject.songguessr.core.handler;

import com.petproject.songguessr.model.BaseEvent;

public interface EventHandler<T extends BaseEvent<?>> {
    boolean canHandle(BaseEvent<?> event);
    Class<T> getType();
}

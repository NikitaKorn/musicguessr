package com.petproject.songguessr.core.handler;

import com.petproject.BaseEvent;

public interface EventHandler<T extends BaseEvent<?>> {
    boolean canHandle(BaseEvent<?> event);
    Class<T> getType();
}

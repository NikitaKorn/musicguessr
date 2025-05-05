package com.petproject.musicguessr.core.handler;

import com.petproject.musicguessr.model.BaseEvent;

public interface EventHandler<T extends BaseEvent<?>> {
    boolean canHandle(BaseEvent<?> event);
    Class<T> getType();
}

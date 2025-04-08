package com.petproject.musicguessr.core.handler;

import com.petproject.musicguessr.model.BaseEvent;

public interface EventHandler<T extends BaseEvent<?>> {
    boolean canHandle(T event) throws ClassCastException;
    Class<T> getType();
}

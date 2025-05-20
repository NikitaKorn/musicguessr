package com.petproject.songguessr.model;

import lombok.Getter;

@Getter
public abstract class BaseEvent<T> {
    protected EventType eventType;

    abstract public T getPayload();
}

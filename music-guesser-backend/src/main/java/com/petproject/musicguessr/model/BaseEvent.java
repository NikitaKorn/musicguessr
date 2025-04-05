package com.petproject.musicguessr.model;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class BaseEvent<T> {
    protected EventType eventType;

    abstract public T getPayload();
}

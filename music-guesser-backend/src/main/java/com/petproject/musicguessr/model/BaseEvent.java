package com.petproject.musicguessr.model;

import lombok.Getter;

@Getter
public abstract class BaseEvent {
    protected EventType eventType;
}

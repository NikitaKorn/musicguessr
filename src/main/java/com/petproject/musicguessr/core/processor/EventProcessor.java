package com.petproject.musicguessr.core.processor;

import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;

import java.util.Set;

public interface EventProcessor<T> {
    void process(T event, Player player, Set<Player> players);
}

package com.petproject.musicguessr.core.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class MessageConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Class<? extends BaseEvent<?>>, List<Enum<EventType>>> map;

    public MessageConverter(Map<Class<? extends BaseEvent<?>>, List<Enum<EventType>>> map) {
        this.map = map;
    }

    public BaseEvent<?> parseRequestEventFromMessage(String message) {
        for (Class<? extends BaseEvent<?>> cl : map.keySet()) {
            BaseEvent<?> res = parseMessage(message, cl);
            if (res != null && map.get(cl).contains(res.getEventType())) {
                return res;
            }
        }
        throw new RuntimeException("Event not found");
    }

    private BaseEvent<?> parseMessage(String message, Class<? extends BaseEvent<?>> cl) {
        try {
            return objectMapper.readValue(message, cl);
        } catch (JsonProcessingException e) {
            log.debug("Parsing failed for {}: {}", cl.getSimpleName(), e.getMessage());
            return null;
        }
    }
}

package com.petproject.songguessr.core.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.EventType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * Класс для преобразования сообщений в события соответствующего типа.
 * Так как любой {@link BaseEvent} может содержать любой {@link EventType}, конвертер позволяет
 * однозначно определить ивент по его типу с помощью словаря {@link MessageConverter#eventToEventType}.
 *
 * <p>
 * Использует {@link ObjectMapper} для десериализации JSON-сообщений в объекты событий, унаследованные от {@link BaseEvent}.
 * Поддерживает проверку допустимых типов событий ({@link EventType}) для каждого класса событий.
 * </p>
 *
 * @see BaseEvent
 * @see EventType
 */
@Slf4j
public class MessageConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Class<? extends BaseEvent<?>>, List<Enum<EventType>>> eventToEventType;

    public MessageConverter(Map<Class<? extends BaseEvent<?>>, List<Enum<EventType>>> eventToEventType) {
        this.eventToEventType = eventToEventType;
    }

    public BaseEvent<?> parseRequestEventFromMessage(String message) {
        for (Class<? extends BaseEvent<?>> cl : eventToEventType.keySet()) {
            BaseEvent<?> res = getEventFromMessage(message, cl);
            if (res != null && eventToEventType.get(cl).contains(res.getEventType())) {
                return res;
            }
        }
        throw new RuntimeException("Event not found");
    }

    private BaseEvent<?> getEventFromMessage(String message, Class<? extends BaseEvent<?>> cl) {
        try {
            return objectMapper.readValue(message, cl);
        } catch (JsonProcessingException e) {
            log.debug("Parsing failed for {}: {}", cl.getSimpleName(), e.getMessage());
            return null;
        }
    }
}

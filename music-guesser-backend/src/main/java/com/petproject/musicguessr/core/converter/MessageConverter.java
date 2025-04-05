package com.petproject.musicguessr.core.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import com.petproject.musicguessr.model.inrequest.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.petproject.musicguessr.model.EventType.*;

@Slf4j
@Service
public class MessageConverter<T extends BaseEvent<?>> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Class, List<Enum<EventType>>> map = Map.of(
            CodeRequestEvent.class, List.of(SHOW_INVITE_CODE_REQUEST_TARGET),
            SearchRequestEvent.class, List.of(SEARCH_REQUEST_TARGET, SEARCH_REQUEST_BROADCAST),
            SongRequestEvent.class, List.of(SONG_REQUEST_TARGET, SONG_REQUEST_BROADCAST),
            WordRequestEvent.class, List.of(WORD_REQUEST_TARGET, WORD_REQUEST_BROADCAST)
    );

    public T parseRequestEventFromMessage(String message) {
        for (Class<T> cl : map.keySet()) {
            T res = parseMessage(message, cl);
            if (res != null) {
                List<Enum<EventType>> events = map.get(cl);
                boolean contains = events.contains(res.getEventType());
                if (contains) {
                    return res;
                }
            }
        }
        throw new RuntimeException("qwe"); // ToDo refactor
    }

    private T parseMessage(String message, Class<T> cl) {
        try {
            return objectMapper.readValue(message, cl);
        } catch (JsonProcessingException e) {
            log.debug("Request message can't be parsed to {}! {}", cl.getName(), e.getMessage());
            return null;
        }
    }
}

package com.petproject.musicguessr.core.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.musicguessr.model.inrequest.RequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RequestEvent parseRequestEventFromMessage(String message) {
        try {
            return objectMapper.readValue(message, RequestEvent.class);
        } catch (JsonProcessingException e) {
            log.error("Request message can't be parsed! {}", e.getMessage());
            return RequestEvent.empty();
        }
    }
}

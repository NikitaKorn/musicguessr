package com.petproject.musicguessr.core.room.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
@AllArgsConstructor
public class Player {
    private final WebSocketSession session;
    private String name;
}

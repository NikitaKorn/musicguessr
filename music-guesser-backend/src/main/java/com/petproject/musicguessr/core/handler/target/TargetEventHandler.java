package com.petproject.musicguessr.core.handler.target;

import com.petproject.musicguessr.model.inrequest.RequestEvent;
import org.springframework.web.socket.WebSocketSession;

public interface TargetEventHandler {
    boolean canHandle(RequestEvent event);
    void handle(RequestEvent event, WebSocketSession session);
}

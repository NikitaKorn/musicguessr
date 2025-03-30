package com.petproject.musicguessr.core.handler.target;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.model.inrequest.RequestEvent;
import com.petproject.musicguessr.model.outrequest.Hit;
import com.petproject.musicguessr.model.response.dto.SearchResultEvent;
import com.petproject.musicguessr.service.genius.GeniusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

import static com.petproject.musicguessr.model.EventType.SEARCH_REQUEST;

@AllArgsConstructor
@Component
public class SearchRequestHandlerTarget implements TargetEventHandler {
    private final EventDispatcher eventDispatcher;
    private final GeniusService geniusService;

    @Override
    public boolean canHandle(RequestEvent event) {
        return SEARCH_REQUEST.equals(event.getEventType());
    }

    @Override
    public void handle(RequestEvent event, WebSocketSession session) {  // ToDo подумать, можно ли избавиться от спринговой зависимости
        List<Hit> hits = geniusService.findByText(event.getMessage());
        eventDispatcher.sendEventToClient(new SearchResultEvent(hits), session);
    }
}

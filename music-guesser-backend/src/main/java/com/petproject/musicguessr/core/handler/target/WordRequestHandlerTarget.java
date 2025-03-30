package com.petproject.musicguessr.core.handler.target;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.model.inrequest.RequestEvent;
import com.petproject.musicguessr.model.response.dto.WordResultEvent;
import com.petproject.musicguessr.service.word.RandomMusicPartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import static com.petproject.musicguessr.model.EventType.WORD_REQUEST;

@AllArgsConstructor
@Component
public class WordRequestHandlerTarget implements TargetEventHandler {
    private final EventDispatcher eventDispatcher;
    private final RandomMusicPartService wordsService;

    @Override
    public boolean canHandle(RequestEvent event) {
        return WORD_REQUEST.equals(event.getEventType());
    }

    @Override
    public void handle(RequestEvent event, WebSocketSession session) {
        String word = wordsService.peekRandom();
        eventDispatcher.sendEventToClient(new WordResultEvent(word), session);
    }
}

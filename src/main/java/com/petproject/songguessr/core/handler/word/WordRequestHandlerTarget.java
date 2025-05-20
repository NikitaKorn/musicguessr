package com.petproject.songguessr.core.handler.word;

import com.petproject.songguessr.core.dispatcher.EventDispatcher;
import com.petproject.songguessr.core.handler.TargetEventHandler;
import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.inrequest.WordRequestEvent;
import com.petproject.songguessr.model.response.dto.WordResultResponseEvent;
import com.petproject.songguessr.service.word.RandomSongPartService;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.petproject.songguessr.model.EventType.WORD_REQUEST_TARGET;

@Component
public final class WordRequestHandlerTarget extends WordRequestHandler implements TargetEventHandler<WordRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public WordRequestHandlerTarget(RandomSongPartService wordsService, EventDispatcher eventDispatcher) {
        super(wordsService);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(BaseEvent<?> event) {
        return event instanceof WordRequestEvent &&
                WORD_REQUEST_TARGET.equals(event.getEventType());
    }

    @Override
    public void handle(WordRequestEvent event, Player player) {
        var word = peekWord();
        eventDispatcher.sendEventToPlayers(new WordResultResponseEvent(word), Collections.singleton(player));
    }

    @Override
    public Class<WordRequestEvent> getType() {
        return WordRequestEvent.class;
    }
}

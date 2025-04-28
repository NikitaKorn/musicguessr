package com.petproject.musicguessr.core.handler.word;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.TargetEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.inrequest.WordRequestEvent;
import com.petproject.musicguessr.model.response.dto.WordResultResponseEvent;
import com.petproject.musicguessr.service.word.RandomSongPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.petproject.musicguessr.model.EventType.WORD_REQUEST_TARGET;

@Component
public final class WordRequestHandlerTarget extends WordRequestHandler implements TargetEventHandler<WordRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public WordRequestHandlerTarget(@Autowired RandomSongPartService wordsService,
                                    @Autowired EventDispatcher eventDispatcher) {
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

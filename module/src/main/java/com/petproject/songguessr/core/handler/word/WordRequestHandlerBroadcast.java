package com.petproject.songguessr.core.handler.word;

import com.petproject.BaseEvent;
import com.petproject.inrequest.WordRequestEvent;
import com.petproject.response.dto.WordResultResponseEvent;
import com.petproject.songguessr.core.dispatcher.EventDispatcher;
import com.petproject.songguessr.core.handler.BroadcastEventHandler;
import com.petproject.songguessr.core.room.model.Player;
import com.petproject.songguessr.service.word.RandomSongPartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.petproject.EventType.WORD_REQUEST_BROADCAST;


@Component
public final class WordRequestHandlerBroadcast extends WordRequestHandler implements BroadcastEventHandler<WordRequestEvent> {
    private static final Logger log = LoggerFactory.getLogger(WordRequestHandlerBroadcast.class);
    private final EventDispatcher eventDispatcher;

    public WordRequestHandlerBroadcast(RandomSongPartService wordsService, EventDispatcher eventDispatcher) {
        super(wordsService);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(BaseEvent<?> event) {
        return event instanceof WordRequestEvent &&
                WORD_REQUEST_BROADCAST.equals(event.getEventType());
    }

    @Override
    public void handle(WordRequestEvent event, Set<Player> players) {
        var word = peekWord();
        eventDispatcher.sendEventToPlayers(new WordResultResponseEvent(word), players);
    }

    @Override
    public Class<WordRequestEvent> getType() {
        return WordRequestEvent.class;
    }
}

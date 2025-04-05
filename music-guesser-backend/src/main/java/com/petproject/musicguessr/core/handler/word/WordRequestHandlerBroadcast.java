package com.petproject.musicguessr.core.handler.word;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.BroadcastEventHandler;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.inrequest.WordRequestEvent;
import com.petproject.musicguessr.model.response.dto.WordResultResponseEvent;
import com.petproject.musicguessr.service.word.RandomSongPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.petproject.musicguessr.model.EventType.WORD_REQUEST_BROADCAST;

@Component
public final class WordRequestHandlerBroadcast extends WordRequestHandler implements BroadcastEventHandler<WordRequestEvent> {
    private final EventDispatcher eventDispatcher;

    public WordRequestHandlerBroadcast(@Autowired RandomSongPartService wordsService,
                                    @Autowired EventDispatcher eventDispatcher) {
        super(wordsService);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean canHandle(WordRequestEvent event) {
        return WORD_REQUEST_BROADCAST.equals(event.getEventType());
    }

    @Override
    public void handle(WordRequestEvent event, Set<Player> players) {
        var word = peekWord();
        eventDispatcher.sendEventToPlayers(new WordResultResponseEvent(word), players);
    }
}

package com.petproject.musicguessr.core.handler.broadcast;

import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.room.model.Player;
import com.petproject.musicguessr.model.inrequest.RequestEvent;
import com.petproject.musicguessr.model.response.dto.WordResultEvent;
import com.petproject.musicguessr.service.word.RandomMusicPartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.petproject.musicguessr.model.EventType.WORD_REQUEST;

@AllArgsConstructor
@Component
public class WordRequestHandlerBroadcast implements BroadcastEventHandler {
    private final EventDispatcher eventDispatcher;
    private final RandomMusicPartService wordsService;

    @Override
    public boolean canHandle(RequestEvent event) {
        return WORD_REQUEST.equals(event.getEventType());
    }

    @Override
    public void handle(RequestEvent event, Set<Player> players) {
        String word = wordsService.peekRandom();
        eventDispatcher.broadcastEvent(new WordResultEvent(word), players);
    }
}

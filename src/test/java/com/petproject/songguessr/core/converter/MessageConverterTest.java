package com.petproject.songguessr.core.converter;

import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.EventType;
import com.petproject.songguessr.model.inrequest.CodeRequestEvent;
import com.petproject.songguessr.model.inrequest.SearchRequestEvent;
import com.petproject.songguessr.model.inrequest.SongRequestEvent;
import com.petproject.songguessr.model.inrequest.WordRequestEvent;
import com.petproject.songguessr.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.petproject.songguessr.model.EventType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MessageConverterTest {
    private static MessageConverter messageConverter;

    @BeforeAll
    public static void init() {
        Map<Class<? extends BaseEvent<?>>, List<Enum<EventType>>> eventToEventType = Map.of(
                CodeRequestEvent.class, List.of(SHOW_INVITE_CODE_REQUEST_TARGET),
                SearchRequestEvent.class, List.of(SEARCH_REQUEST_TARGET, SEARCH_REQUEST_BROADCAST),
                SongRequestEvent.class, List.of(SONG_REQUEST_TARGET, SONG_REQUEST_BROADCAST),
                WordRequestEvent.class, List.of(WORD_REQUEST_TARGET, WORD_REQUEST_BROADCAST)
        );

        messageConverter = new MessageConverter(eventToEventType);
    }

    @Test
    public void parseSearchEventMessage() {
        String requestMessage = TestUtils.getSearchRequestTargetMessage("qwe");
        BaseEvent<?> baseEvent = messageConverter.parseRequestEventFromMessage(requestMessage);
        assertThat(
                baseEvent,
                instanceOf(SearchRequestEvent.class)
        );
    }

    @Test
    public void parseSongEventMessage() {
        String requestMessage = TestUtils.getSongRequestTargetMessage("qwe");
        BaseEvent<?> baseEvent = messageConverter.parseRequestEventFromMessage(requestMessage);
        assertThat(
                baseEvent,
                instanceOf(SongRequestEvent.class)
        );
    }
}

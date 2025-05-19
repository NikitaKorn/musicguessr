package com.petproject.musicguessr.core.room;

import com.petproject.musicguessr.AbstractTest;
import com.petproject.musicguessr.model.EventType;
import com.petproject.musicguessr.service.registry.GameRoomsRegistry;
import com.petproject.musicguessr.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.ArrayList;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SoloRoomTest extends AbstractTest {
    private final WebSocketClientBundle bundle;
    private final GameRoomsRegistry roomsRegistry;

    public SoloRoomTest(@Autowired GameRoomsRegistry roomsRegistry) {
        bundle = new WebSocketClientBundle(new StandardWebSocketClient(), new SimpleWebSocketHandler(), new ArrayList<>());
        this.roomsRegistry = roomsRegistry;
    }

    @BeforeEach
    public void before() {
        connectAll(bundle, roomsRegistry.findFreeSoloGameRoom().getPath());
    }

    @AfterEach
    public void after() {
        closeAllSessions(bundle);
    }

    @Test
    public void processWordResponseEventInSoloRoomTest() {
        broadcast(bundle, TestUtils.getWordRequestTargetMessage());
        String responseMessage = bundle.handler().getLastMessageAndClear();

        assertThat(
                responseMessage,
                allOf(
                        hasJsonPath("$.eventType", equalTo(EventType.WORD_RESPONSE.getText())),
                        hasJsonPath("$.payload.word", anyOf(
                                equalTo("сердце"),
                                equalTo("любовь"),
                                equalTo("ночь")
                        ))
                )
        );
    }

    @Test
    public void processSearchResponseEventInSoloRoomTest() {
        broadcast(bundle, TestUtils.getRequestMessage(EventType.SEARCH_REQUEST_TARGET, "qwe"));
        String responseMessage = bundle.handler().getLastMessageAndClear();

        assertThat(
                responseMessage,
                allOf(
                        hasJsonPath("$.payload.hits", hasSize(greaterThan(0))),
                        hasJsonPath("$.payload.hits[*].result", everyItem(notNullValue()))
                )
        );
    }

    @Test
    public void processSongResponseEventInSoloRoomTest() {
        broadcast(bundle, TestUtils.getRequestMessage(EventType.SONG_REQUEST_TARGET, "7407364"));
        String responseMessage = bundle.handler().getLastMessageAndClear();

        assertThat(
                responseMessage,
                allOf(
                        hasJsonPath("$.eventType", equalTo(EventType.SONG_RESPONSE.getText())),
                        hasJsonPath("$.payload.title", equalToIgnoringCase("qwerty lang")),
                        hasJsonPath("$.payload.url", notNullValue())
                )
        );
    }

    @Test
    public void processInviteCodeResponseEventInSoloRoomTest() {
        broadcast(bundle, TestUtils.getCodeRequestTargetMessage());
        String responseMessage = bundle.handler().getLastMessageAndClear();

        assertThat(
                responseMessage,
                allOf(
                        hasJsonPath("$.eventType", equalTo(EventType.SHOW_INVITE_CODE_RESPONSE.getText())),
                        hasJsonPath("$.payload.code", equalTo("")) // Because solo room doesn't need invite code
                )
        );
    }
}

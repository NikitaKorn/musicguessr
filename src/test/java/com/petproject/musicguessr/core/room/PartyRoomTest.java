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
import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PartyRoomTest extends AbstractTest {
    private final GameRoomsRegistry roomsRegistry;
    private final List<WebSocketClientBundle> bundles;

    public PartyRoomTest(@Autowired GameRoomsRegistry roomsRegistry) {
        this.roomsRegistry = roomsRegistry;
        bundles = List.of(
                new WebSocketClientBundle(new StandardWebSocketClient(), new SimpleWebSocketHandler(), new ArrayList<>()),
                new WebSocketClientBundle(new StandardWebSocketClient(), new SimpleWebSocketHandler(), new ArrayList<>()),
                new WebSocketClientBundle(new StandardWebSocketClient(), new SimpleWebSocketHandler(), new ArrayList<>())
        );
    }

    @BeforeEach
    public void before() {
        connectAll(bundles, roomsRegistry.findFreePartyGameRoom().getPath());
    }

    @AfterEach
    public void after() {
        closeAllSessions(bundles);
    }

    @Test
    public void processWordResponseEventInSoloRoomTest() {
        broadcastAll(bundles, TestUtils.getWordRequestBroadcastMessage());
        bundles.forEach(webSocketPair -> {
                    String responseMessage = webSocketPair.handler().getLastMessageAndClear();
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
        );
    }

    @Test
    public void processSearchResponseEventInSoloRoomTest() {
        broadcastAll(bundles, TestUtils.getRequestMessage(EventType.SEARCH_REQUEST_BROADCAST, "qwe"));
        bundles.forEach(webSocketPair -> {
            String responseMessage = webSocketPair.handler().getLastMessageAndClear();
            assertThat(
                    responseMessage,
                    allOf(
                            hasJsonPath("$.payload.hits", hasSize(greaterThan(0))),
                            hasJsonPath("$.payload.hits[*].result", everyItem(notNullValue()))
                    )
            );
        });
    }

    @Test
    public void processSongResponseEventInSoloRoomTest() {
        broadcastAll(bundles, TestUtils.getRequestMessage(EventType.SONG_REQUEST_TARGET, "7407364"));
        bundles.forEach(webSocketPair -> {
            String responseMessage = webSocketPair.handler().getLastMessageAndClear();
            assertThat(
                    responseMessage,
                    allOf(
                            hasJsonPath("$.eventType", equalTo(EventType.SONG_RESPONSE.getText())),
                            hasJsonPath("$.payload.title", equalToIgnoringCase("qwerty lang")),
                            hasJsonPath("$.payload.url", notNullValue())
                    )
            );
        });
    }

    @Test
    public void processInviteCodeResponseEventInSoloRoomTest() {
        broadcastAll(bundles, TestUtils.getCodeRequestTargetMessage());
        bundles.forEach(webSocketPair -> {
            String responseMessage = webSocketPair.handler().getLastMessageAndClear();
            assertThat(
                    responseMessage,
                    allOf(
                            hasJsonPath("$.eventType", equalTo(EventType.SHOW_INVITE_CODE_RESPONSE.getText())),
                            hasJsonPath("$.payload.code", notNullValue())
                    )
            );
        });
    }
}

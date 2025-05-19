package com.petproject.musicguessr;

import lombok.NonNull;
import org.awaitility.Awaitility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * Абстрактный класс теста от которого наследуются все тесты нуждающиеся в Spring Context
 * <p>
 * Т.е. при любом изменении конфигурации контекста (например разные аннотации) поднимается новый контекст
 * все аннотации сделаны в этом классе - это гарантирует поднятие ровно одного контекста для всех тестов
 */
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class AbstractTest {
    public record WebSocketClientBundle(StandardWebSocketClient client,
                                        SimpleWebSocketHandler handler,
                                        List<WebSocketSession> sessions) {}

    private static final Logger log = LoggerFactory.getLogger(AbstractTest.class); //NOSONAR

    protected void connectAll(WebSocketClientBundle bundle, String roomPath) {
        String uri = "ws://localhost:8080/" + roomPath;
        try {
            WebSocketSession session = bundle.client.execute(bundle.handler, uri).get();
            bundle.sessions.add(session);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Can't connect to {}! {}", uri, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void connectAll(List<WebSocketClientBundle> bundles, String roomPath) {
        bundles.forEach(bundle -> connectAll(bundle, roomPath));
    }

    protected void broadcast(WebSocketClientBundle bundle, String message) {
        List<WebSocketSession> sessions = bundle.sessions;
        sessions.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(message));
                Awaitility.await().until(() -> bundle.handler.lastMessage != null);
            } catch (IOException e) {
                log.error("Can't send message to session with ID {}! {}", session.getId(), e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    protected void broadcastAll(List<WebSocketClientBundle> bundles, String message) {
        bundles.forEach(webSocket -> broadcast(webSocket, message));
    }

    protected void closeAllSessions(WebSocketClientBundle bundle) {
        bundle.sessions.forEach(session -> {
            try {
                session.close();
            } catch (IOException e) {
                log.error("Can't close session with id {}", session.getId());
                throw new RuntimeException(e);
            }
        });
        bundle.sessions.clear();
    }

    protected void closeAllSessions(List<WebSocketClientBundle> bundles) {
        bundles.forEach(this::closeAllSessions);
    }

    public static class SimpleWebSocketHandler extends TextWebSocketHandler {
        private String lastMessage;

        public String getLastMessageAndClear() {
            if (lastMessage == null) {
                log.warn("Last message is null!");
            }
            String temp = lastMessage;
            lastMessage = null;
            return temp;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            log.info("Connected to session with ID {}", session.getId());
        }

        @Override
        protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
            log.info("Message was received");
            lastMessage = message.getPayload();
        }
    }
}

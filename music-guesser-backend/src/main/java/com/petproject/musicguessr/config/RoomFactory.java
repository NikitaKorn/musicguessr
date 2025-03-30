package com.petproject.musicguessr.config;

import com.petproject.musicguessr.core.processor.PartyEventProcessor;
import com.petproject.musicguessr.core.processor.SoloEventProcessor;
import com.petproject.musicguessr.core.room.adapter.WebSocketHandlerAdapter;
import com.petproject.musicguessr.core.room.handler.PartySessionRoomHandler;
import com.petproject.musicguessr.core.room.handler.SessionRoomHandler;
import com.petproject.musicguessr.core.room.handler.SoloSessionRoomHandler;
import com.petproject.musicguessr.model.GameSessionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.stream.IntStream;

/**
 * Configuration class, which responsibility to create session rooms with specific path, based on WebSocket's.
 * After creation, session room info save in room registry service.
 */
@Slf4j
@Configuration
@EnableWebSocket
public class RoomFactory implements WebSocketConfigurer {
    private final GameSessionRoomRegistry roomRegistry;
    private final SoloEventProcessor soloEventProcessor;
    private final PartyEventProcessor partyEventProcessor;
    private final int soloRoomCount;
    private final int partyRoomCount;

    public RoomFactory(
            @Autowired GameSessionRoomRegistry roomRegistry,
            @Autowired SoloEventProcessor soloEventProcessor,
            @Autowired PartyEventProcessor partyEventProcessor,
            @Value("${service.configuration.room-initializer.solo-room-count}") int soloRoomCount,
            @Value("${service.configuration.room-initializer.party-room-count}") int partyRoomCount
    ) {
        this.roomRegistry = roomRegistry;
        this.soloRoomCount = soloRoomCount;
        this.partyRoomCount = partyRoomCount;
        this.soloEventProcessor = soloEventProcessor;
        this.partyEventProcessor = partyEventProcessor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        createSoloRooms(registry);
        createPartyRooms(registry);
        log.info("Total created rooms count {}", roomRegistry.getRoomsCount());
    }

    private void createSoloRooms(WebSocketHandlerRegistry registry) {
        IntStream.range(0, soloRoomCount).forEach((l) -> {
            SoloSessionRoomHandler gameSessionRoom = createSoloSessionRoomHandler();
            WebSocketHandlerAdapter webSocketHandlerAdapter = createWebSocketHandlerAdapter(gameSessionRoom);

            GameSessionModel<SoloSessionRoomHandler> model = GameSessionModel.<SoloSessionRoomHandler>builder()
                    .roomId(gameSessionRoom.getRoomId())
                    .handler(gameSessionRoom)
                    .adapter(webSocketHandlerAdapter)
                    .type(GameSessionModel.Type.SOLO)
                    .isBusy(false)
                    .build();

            registry.addHandler(createWebSocketHandlerAdapter(gameSessionRoom), model.getPath()).setAllowedOrigins("*");
            roomRegistry.addGameSessionRoom(gameSessionRoom.getRoomId(), model);
        });
        log.info("Was create {} solo rooms", soloRoomCount);
    }

    private void createPartyRooms(WebSocketHandlerRegistry registry) {
        IntStream.range(0, partyRoomCount).forEach((l) -> {
            PartySessionRoomHandler gameSessionRoom = createPartySessionRoomHandler();
            WebSocketHandlerAdapter webSocketHandlerAdapter = createWebSocketHandlerAdapter(gameSessionRoom);

            GameSessionModel<PartySessionRoomHandler> model = GameSessionModel.<PartySessionRoomHandler>builder()
                    .roomId(gameSessionRoom.getRoomId())
                    .handler(gameSessionRoom)
                    .adapter(webSocketHandlerAdapter)
                    .type(GameSessionModel.Type.PARTY)
                    .isBusy(false)
                    .build();

            registry.addHandler(createWebSocketHandlerAdapter(gameSessionRoom), model.getPath()).setAllowedOrigins("*");
            roomRegistry.addGameSessionRoom(gameSessionRoom.getRoomId(), model);
        });
        log.info("Was create {} party rooms", partyRoomCount);
    }

    @Lookup
    public SoloSessionRoomHandler createSoloSessionRoomHandler(){
        return null;
    }

    @Lookup
    public PartySessionRoomHandler createPartySessionRoomHandler(){
        return null;
    }

    @Lookup
    public WebSocketHandlerAdapter createWebSocketHandlerAdapter(SessionRoomHandler sessionRoomHandler){
        return null;
    }
}

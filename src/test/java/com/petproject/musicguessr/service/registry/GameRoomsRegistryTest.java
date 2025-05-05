package com.petproject.musicguessr.service.registry;

import com.petproject.musicguessr.AbstractTest;
import com.petproject.musicguessr.exception.RoomIsBusyException;
import com.petproject.musicguessr.model.GameRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

public class GameRoomsRegistryTest extends AbstractTest {
    @Autowired
    private Environment env;
    @Autowired
    private GameRoomsRegistry<?> roomsRegistry;

    @BeforeEach
    public void before(){
        roomsRegistry.getGameRooms().values().forEach(room -> room.setBusy(false));
    }

    @Test
    public void successInitRooms() {
        String soloRoomCount = env.getProperty("service.configuration.room-initializer.solo-room-count");
        String partyRoomCount = env.getProperty("service.configuration.room-initializer.party-room-count");
        int totalRooms = Integer.parseInt(soloRoomCount) + Integer.parseInt(partyRoomCount);
        assertEquals(totalRooms, roomsRegistry.getRoomsCount());
    }

    @Test
    public void findFreeSoloRoom() {
        GameRoom<?> freeSoloGameRoom = roomsRegistry.findFreeSoloGameRoom();
        assertNotNull(freeSoloGameRoom);
        assertFalse(freeSoloGameRoom.isBusy());
    }

    @Test
    public void throwExBecauseAllSoloRoomsAreBusy() {
        roomsRegistry.getGameRooms().values().forEach(room -> room.setBusy(true));
        assertThrows(RoomIsBusyException.class, () -> roomsRegistry.findFreeSoloGameRoom());
    }

    @Test
    public void throwExBecauseAllPartyRoomsAreBusy() {
        roomsRegistry.getGameRooms().values().forEach(room -> room.setBusy(true));
        assertThrows(RoomIsBusyException.class, () -> roomsRegistry.findFreePartyGameRoom());
    }
}

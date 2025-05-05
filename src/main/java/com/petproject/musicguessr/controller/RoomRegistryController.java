package com.petproject.musicguessr.controller;

import com.petproject.musicguessr.service.registry.GameRoomsRegistry;
import com.petproject.musicguessr.model.GameRoom;
import com.petproject.musicguessr.model.response.RoomResponse;
import com.petproject.musicguessr.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sessions")
@AllArgsConstructor
public class RoomRegistryController {
    private GameRoomsRegistry roomRegistry;

    @GetMapping("/peek-solo-session-room")
    public RoomResponse peekSoloFreeRoom() {
        var room = roomRegistry.findFreeSoloGameRoom();
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setPath(room.getPath());
        return roomResponse;
    }

    @GetMapping("/peek-party-session-room")
    public RoomResponse peekPartyFreeRoom(@RequestParam(required = false, defaultValue = "") String inviteCode) {
        GameRoom room = null;
        if (AppUtils.isCodeExist(inviteCode)) {
            room = roomRegistry.findFreePartyGameRoom();
        } else {
            room = roomRegistry.findGameRoomByInviteCode(inviteCode);
        }
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setPath(room.getPath());
        return roomResponse;
    }
}

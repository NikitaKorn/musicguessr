package com.petproject.musicguessr.controller;

import com.petproject.musicguessr.config.GameSessionRoomRegistry;
import com.petproject.musicguessr.model.GameSessionModel;
import com.petproject.musicguessr.model.response.RoomResponse;
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
    private GameSessionRoomRegistry roomRegistry;

    @GetMapping("/peek-solo-session-room")
    public RoomResponse peekSoloFreeRoom() {
        var room = roomRegistry.findSoloFreeRoom();
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setPath(room.getPath());
        return roomResponse;
    }

    @GetMapping("/peek-party-session-room")
    public RoomResponse peekPartyFreeRoom(@RequestParam(required=false, defaultValue="") String inviteCode) {
        GameSessionModel room = null;
        if(inviteCode.equals("null")){
            room = roomRegistry.findPartyFreeRoom();
        } else {
            room = roomRegistry.findRoomOnInviteCode(inviteCode);
        }
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setPath(room.getPath());
        return roomResponse;
    }
}

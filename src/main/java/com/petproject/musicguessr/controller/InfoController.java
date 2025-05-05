package com.petproject.musicguessr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @GetMapping("/info")
    public String peekSoloFreeRoom() {
        return "alive";
    }
}

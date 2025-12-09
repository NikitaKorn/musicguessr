package com.petproject.songguessr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {
    @GetMapping("/liveness")
    public String liveness() {
        return "alive";
    }
}

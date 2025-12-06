package com.petproject.songguessr.core.handler.word;

import com.petproject.songguessr.service.word.RandomSongPartService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WordRequestHandler {
    protected final RandomSongPartService wordsService;

    protected String peekWord() {
        return wordsService.peekRandom();
    }
}

package com.petproject.musicguessr.core.handler.word;

import com.petproject.musicguessr.service.word.RandomSongPartService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WordRequestHandler {
    protected final RandomSongPartService wordsService;

    protected String peekWord() {
        return wordsService.peekRandom();
    }
}

package com.petproject.musicguessr.service.fabric;

import com.petproject.musicguessr.service.code.InviteCodeServiceImpl;
import com.petproject.musicguessr.service.genius.GeniusService;
import com.petproject.musicguessr.service.word.WordsService;

public interface GameSessionFactory {
    GeniusService createGeniusService();
    WordsService createWordsService();
    InviteCodeServiceImpl createInviteCodeService();
}

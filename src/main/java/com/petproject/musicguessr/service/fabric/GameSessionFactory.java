package com.petproject.musicguessr.service.fabric;

import com.petproject.musicguessr.service.code.InviteCodeServiceImpl;
import com.petproject.musicguessr.service.genius.GeniusService;
import com.petproject.musicguessr.service.word.RandomPeekService;

public interface GameSessionFactory {
    GeniusService createGeniusService();
    RandomPeekService createWordsService();
    InviteCodeServiceImpl createInviteCodeService();
}

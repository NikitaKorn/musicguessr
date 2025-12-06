package com.petproject.songguessr.service.fabric;

import com.petproject.songguessr.service.code.InviteCodeServiceImpl;
import com.petproject.songguessr.service.genius.GeniusService;
import com.petproject.songguessr.service.word.RandomPeekService;

public interface GameSessionFactory {
    GeniusService createGeniusService();
    RandomPeekService createWordsService();
    InviteCodeServiceImpl createInviteCodeService();
}

package com.petproject.songguessr.service.fabric;

import com.petproject.songguessr.service.code.InviteCodeServiceImpl;
import com.petproject.songguessr.service.genius.GeniusService;
import com.petproject.songguessr.service.word.RandomPeekService;
import lombok.Getter;
import lombok.Setter;

@Getter
public final class SoloGameSessionFactory implements GameSessionFactory {
    private final GeniusService geniusService;
    private final int totalRounds;
    @Setter
    private final boolean isNeedTimer = true;

    public SoloGameSessionFactory(GeniusService geniusService, int totalRounds) {
        this.geniusService = geniusService;
        this.totalRounds = totalRounds;
    }

    @Override
    public GeniusService createGeniusService() {
        return null;
    }

    @Override
    public RandomPeekService createWordsService() {
        return null;
    }

    @Override
    public InviteCodeServiceImpl createInviteCodeService() {
        return null;
    }
}

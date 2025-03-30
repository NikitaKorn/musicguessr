package com.petproject.musicguessr.service.fabric;

import com.petproject.musicguessr.service.code.InviteCodeServiceImpl;
import com.petproject.musicguessr.service.genius.GeniusService;
import com.petproject.musicguessr.service.word.WordsService;
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
    public WordsService createWordsService() {
        return null;
    }

    @Override
    public InviteCodeServiceImpl createInviteCodeService() {
        return null;
    }
}

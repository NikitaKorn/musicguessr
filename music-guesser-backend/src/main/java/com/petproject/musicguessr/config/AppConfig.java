package com.petproject.musicguessr.config;

import com.petproject.musicguessr.client.GeniusClient;
import com.petproject.musicguessr.core.converter.MessageConverter;
import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.broadcast.BroadcastEventHandler;
import com.petproject.musicguessr.core.handler.broadcast.SearchRequestHandlerBroadcast;
import com.petproject.musicguessr.core.handler.broadcast.SongRequestHandlerBroadcast;
import com.petproject.musicguessr.core.handler.broadcast.WordRequestHandlerBroadcast;
import com.petproject.musicguessr.core.handler.target.*;
import com.petproject.musicguessr.core.processor.PartyEventProcessor;
import com.petproject.musicguessr.core.processor.SoloEventProcessor;
import com.petproject.musicguessr.service.genius.GeniusService;
import com.petproject.musicguessr.service.genius.GeniusServiceImpl;
import com.petproject.musicguessr.service.word.RandomMusicPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@EnableAspectJAutoProxy
@Configuration
public class AppConfig {

    @Bean
    public SoloEventProcessor soloEventProcessor(
            @Autowired MessageConverter messageConverter,
            @Autowired SearchRequestHandlerTarget searchRequestHandler,
            @Autowired SongRequestHandlerTarget songRequestHandler,
            @Autowired WordRequestHandlerTarget wordRequestHandler
    ) {
        List<TargetEventHandler> targetHandlers = List.of(
                searchRequestHandler,
                songRequestHandler,
                wordRequestHandler
        );

        return new SoloEventProcessor(messageConverter, targetHandlers);
    }

    @Bean
    public PartyEventProcessor partyEventProcessor(
            @Autowired MessageConverter messageConverter,
            @Autowired ShowInviteCodeRequestHandlerTarget showInviteCodeRequestHandler,
            @Autowired SearchRequestHandlerBroadcast searchRequestHandler,
            @Autowired SongRequestHandlerBroadcast songRequestHandler,
            @Autowired WordRequestHandlerBroadcast wordRequestHandler
    ) {
        List<TargetEventHandler> targetHandlers = List.of(
                showInviteCodeRequestHandler
        );

        List<BroadcastEventHandler> broadcastHandlers = List.of(
                searchRequestHandler,
                songRequestHandler,
                wordRequestHandler
        );

        return new PartyEventProcessor(messageConverter, targetHandlers, broadcastHandlers);
    }

    @Bean
    public SongRequestHandlerBroadcast songRequestHandler(@Autowired GeniusService geniusService) {
        return new SongRequestHandlerBroadcast(eventDispatcher(), geniusService);
    }

    @Bean
    public EventDispatcher eventDispatcher() {
        return new EventDispatcher();
    }

    @Bean
    public GeniusService geniusService(@Autowired GeniusClient geniusClient) {
        return new GeniusServiceImpl(geniusClient);
    }

    @Bean
    public RandomMusicPartService wordsService() {
        return new RandomMusicPartService();
    }
}

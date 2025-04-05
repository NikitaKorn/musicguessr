package com.petproject.musicguessr.config;

import com.petproject.musicguessr.client.GeniusClient;
import com.petproject.musicguessr.core.dispatcher.EventDispatcher;
import com.petproject.musicguessr.core.handler.TargetEventHandler;
import com.petproject.musicguessr.core.handler.BroadcastEventHandler;
import com.petproject.musicguessr.core.handler.code.ShowInviteCodeRequestHandlerTarget;
import com.petproject.musicguessr.core.handler.search.SearchRequestHandlerBroadcast;
import com.petproject.musicguessr.core.handler.search.SearchRequestHandlerTarget;
import com.petproject.musicguessr.core.handler.song.SongRequestHandlerBroadcast;
import com.petproject.musicguessr.core.handler.song.SongRequestHandlerTarget;
import com.petproject.musicguessr.core.handler.word.WordRequestHandlerBroadcast;
import com.petproject.musicguessr.core.handler.word.WordRequestHandlerTarget;
import com.petproject.musicguessr.core.processor.EventProcessor;
import com.petproject.musicguessr.core.processor.EventProcessorImpl;
import com.petproject.musicguessr.service.genius.GeniusService;
import com.petproject.musicguessr.service.genius.GeniusServiceImpl;
import com.petproject.musicguessr.service.word.RandomSongPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@EnableAspectJAutoProxy
@Configuration
public class AppConfig {

    @Bean
    public EventProcessor<?> eventProcessor (
            @Autowired ShowInviteCodeRequestHandlerTarget showInviteCodeRequestHandlerTarget,
            @Autowired SearchRequestHandlerTarget searchRequestHandlerTarget,
            @Autowired SongRequestHandlerTarget songRequestHandlerTarget,
            @Autowired WordRequestHandlerTarget wordRequestHandlerTarget,

            @Autowired SearchRequestHandlerBroadcast searchRequestHandlerBroadcast,
            @Autowired SongRequestHandlerBroadcast songRequestHandlerBroadcast,
            @Autowired WordRequestHandlerBroadcast wordRequestHandlerBroadcast
    ) {
        List<TargetEventHandler<?>> targetHandlers = List.of(
                showInviteCodeRequestHandlerTarget,
                searchRequestHandlerTarget,
                songRequestHandlerTarget,
                wordRequestHandlerTarget
        );

        List<BroadcastEventHandler<?>> broadcastHandlers = List.of(
                searchRequestHandlerBroadcast,
                songRequestHandlerBroadcast,
                wordRequestHandlerBroadcast
        );

        return new EventProcessorImpl(targetHandlers, broadcastHandlers);
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
    public RandomSongPartService wordsService() {
        return new RandomSongPartService();
    }
}

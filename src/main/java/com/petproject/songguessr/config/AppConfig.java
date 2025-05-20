package com.petproject.songguessr.config;

import com.petproject.songguessr.client.GeniusClient;
import com.petproject.songguessr.core.converter.MessageConverter;
import com.petproject.songguessr.core.dispatcher.EventDispatcherImpl;
import com.petproject.songguessr.core.handler.TargetEventHandler;
import com.petproject.songguessr.core.handler.BroadcastEventHandler;
import com.petproject.songguessr.core.handler.code.ShowInviteCodeRequestHandlerTarget;
import com.petproject.songguessr.core.handler.error.ErrorHandlerTarget;
import com.petproject.songguessr.core.handler.search.SearchRequestHandlerBroadcast;
import com.petproject.songguessr.core.handler.search.SearchRequestHandlerTarget;
import com.petproject.songguessr.core.handler.song.SongRequestHandlerBroadcast;
import com.petproject.songguessr.core.handler.song.SongRequestHandlerTarget;
import com.petproject.songguessr.core.handler.word.WordRequestHandlerBroadcast;
import com.petproject.songguessr.core.handler.word.WordRequestHandlerTarget;
import com.petproject.songguessr.core.processor.EventProcessor;
import com.petproject.songguessr.core.processor.EventProcessorImpl;
import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.EventType;
import com.petproject.songguessr.model.inrequest.CodeRequestEvent;
import com.petproject.songguessr.model.inrequest.SearchRequestEvent;
import com.petproject.songguessr.model.inrequest.SongRequestEvent;
import com.petproject.songguessr.model.inrequest.WordRequestEvent;
import com.petproject.songguessr.service.genius.GeniusService;
import com.petproject.songguessr.service.genius.GeniusServiceImpl;
import com.petproject.songguessr.service.word.RandomSongPartService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;
import java.util.Map;

import static com.petproject.songguessr.model.EventType.*;

@EnableAspectJAutoProxy
@Configuration
public class AppConfig {

    @Bean
    public EventProcessor<?> eventProcessor (
            ShowInviteCodeRequestHandlerTarget showInviteCodeRequestHandlerTarget,
            SearchRequestHandlerTarget searchRequestHandlerTarget,
            SongRequestHandlerTarget songRequestHandlerTarget,
            WordRequestHandlerTarget wordRequestHandlerTarget,
            ErrorHandlerTarget errorHandlerTarget,

            SearchRequestHandlerBroadcast searchRequestHandlerBroadcast,
            SongRequestHandlerBroadcast songRequestHandlerBroadcast,
            WordRequestHandlerBroadcast wordRequestHandlerBroadcast
    ) {
        List<TargetEventHandler<?>> targetHandlers = List.of(
                showInviteCodeRequestHandlerTarget,
                searchRequestHandlerTarget,
                songRequestHandlerTarget,
                wordRequestHandlerTarget,
                errorHandlerTarget
        );

        List<BroadcastEventHandler<?>> broadcastHandlers = List.of(
                searchRequestHandlerBroadcast,
                songRequestHandlerBroadcast,
                wordRequestHandlerBroadcast
        );

        return new EventProcessorImpl(targetHandlers, broadcastHandlers);
    }

    @Bean
    public MessageConverter messageConverter() {
        Map<Class<? extends BaseEvent<?>>, List<Enum<EventType>>> map = Map.of(
                CodeRequestEvent.class, List.of(SHOW_INVITE_CODE_REQUEST_TARGET),
                SearchRequestEvent.class, List.of(SEARCH_REQUEST_TARGET, SEARCH_REQUEST_BROADCAST),
                SongRequestEvent.class, List.of(SONG_REQUEST_TARGET, SONG_REQUEST_BROADCAST),
                WordRequestEvent.class, List.of(WORD_REQUEST_TARGET, WORD_REQUEST_BROADCAST)
        );

        return new MessageConverter(map);
    }

    @Bean
    public SongRequestHandlerBroadcast songRequestHandler(GeniusService geniusService) {
        return new SongRequestHandlerBroadcast(eventDispatcher(), geniusService);
    }

    @Bean
    public EventDispatcherImpl eventDispatcher() {
        return new EventDispatcherImpl();
    }

    @Bean
    public GeniusService geniusService(GeniusClient geniusClient) {
        return new GeniusServiceImpl(geniusClient);
    }

    @Bean
    public RandomSongPartService wordsService() {
        return new RandomSongPartService();
    }
}

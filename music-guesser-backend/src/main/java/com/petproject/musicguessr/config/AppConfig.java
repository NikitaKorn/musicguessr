package com.petproject.musicguessr.config;

import com.petproject.musicguessr.client.GeniusClient;
import com.petproject.musicguessr.core.converter.MessageConverter;
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
import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import com.petproject.musicguessr.model.inrequest.CodeRequestEvent;
import com.petproject.musicguessr.model.inrequest.SearchRequestEvent;
import com.petproject.musicguessr.model.inrequest.SongRequestEvent;
import com.petproject.musicguessr.model.inrequest.WordRequestEvent;
import com.petproject.musicguessr.service.genius.GeniusService;
import com.petproject.musicguessr.service.genius.GeniusServiceImpl;
import com.petproject.musicguessr.service.word.RandomSongPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;
import java.util.Map;

import static com.petproject.musicguessr.model.EventType.*;

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
        List<TargetEventHandler<? extends BaseEvent<?>>> targetHandlers = List.of(
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

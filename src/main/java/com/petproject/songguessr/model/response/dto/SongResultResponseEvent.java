package com.petproject.songguessr.model.response.dto;

import com.petproject.songguessr.model.BaseEvent;
import com.petproject.songguessr.model.EventType;
import com.petproject.songguessr.model.outrequest.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

@Getter
public class SongResultResponseEvent extends BaseEvent<SongResultResponseEvent.Payload> {
    private Payload payload;

    public SongResultResponseEvent(Song song) {
        this.eventType = EventType.SONG_RESPONSE;
        this.payload = new Payload();
        this.payload.title = song.getTitle();
        if(!CollectionUtils.isEmpty(song.getMedia())){
            this.payload.url = song.getMedia().get(0).getMediaUrl();
        }
    }

    private SongResultResponseEvent(){}

    @Getter
    @AllArgsConstructor
    @Setter
    @NoArgsConstructor
    public static class Payload {
        private String title;
        private String url;
    }
}

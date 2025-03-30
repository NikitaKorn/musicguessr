package com.petproject.musicguessr.model.response.dto;

import com.petproject.musicguessr.model.BaseEvent;
import com.petproject.musicguessr.model.EventType;
import com.petproject.musicguessr.model.outrequest.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

@Getter
public class SongResultEvent extends BaseEvent {
    private Payload payload;

    public SongResultEvent(Song song) {
        this.eventType = EventType.SONG_RESPONSE;
        this.payload = this.new Payload();
        this.payload.title = song.getTitle();
        if(!CollectionUtils.isEmpty(song.getMedia())){
            this.payload.url = song.getMedia().get(0).getMediaUrl();
        }
    }

    private SongResultEvent(){}

    @Getter
    @AllArgsConstructor
    @Setter
    @NoArgsConstructor
    public class Payload {
        private String title;
        private String url;
    }
}

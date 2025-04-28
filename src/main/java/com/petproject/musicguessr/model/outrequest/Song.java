package com.petproject.musicguessr.model.outrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class Song {
    @JsonProperty("artist_names")
    private String artistName;
    private String title;
    @JsonProperty("apple_music_player_url")
    private String appleMusicPlayerUrl;
    @JsonProperty("url")
    private String geniusUrl;
    private List<Media> media;

    private Song(Builder builder) {
        this.artistName = builder.artistName;
        this.title = builder.title;
        this.appleMusicPlayerUrl = builder.appleMusicPlayerUrl;
        this.geniusUrl = builder.geniusUrl;
        this.media = builder.media;
    }

    // Builder для Song
    public static class Builder {
        private String artistName;
        private String title;
        private String appleMusicPlayerUrl;
        private String geniusUrl;
        private List<Media> media;

        public Builder artistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder appleMusicPlayerUrl(String appleMusicPlayerUrl) {
            this.appleMusicPlayerUrl = appleMusicPlayerUrl;
            return this;
        }

        public Builder geniusUrl(String geniusUrl) {
            this.geniusUrl = geniusUrl;
            return this;
        }

        public Builder media(List<Media> media) {
            this.media = media;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }

    // Внутренний класс Media
    @Getter
    @NoArgsConstructor
    public static class Media {
        private String provider;
        private String type;
        private int typePriority;
        @JsonProperty("url")
        private String mediaUrl;

        // Builder для Media
        public static class MediaBuilder {
            private String provider;
            private String type;
            private String mediaUrl;

            public MediaBuilder provider(String provider) {
                this.provider = provider;
                return this;
            }

            public MediaBuilder type(String type) {
                this.type = type;
                return this;
            }

            public MediaBuilder mediaUrl(String mediaUrl) {
                this.mediaUrl = mediaUrl;
                return this;
            }

            public Media build() {
                Media media = new Media();
                media.provider = this.provider;
                media.type = this.type;
                media.mediaUrl = this.mediaUrl;
                media.typePriority = getMediaTypePriority(type);
                return media;
            }

            private int getMediaTypePriority(String mediaType){
                switch (mediaType){
                    case "audio" -> {
                        return 1;
                    }
                    case "video" -> {
                        return 2;
                    }
                    default -> {
                        return 0;
                    }
                }
            }
        }

        public static MediaBuilder builder() {
            return new MediaBuilder();
        }
    }
}


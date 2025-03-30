package com.petproject.musicguessr.model.outrequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SongsGeniusResponse {
    private Response response;

    @Getter
    @NoArgsConstructor
    public static class Response{
        private Song song;
    }
}

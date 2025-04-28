package com.petproject.musicguessr.model.outrequest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchGeniusResponse {
    private Response response;

    @Data
    @NoArgsConstructor
    public static class Response{
        private List<Hit> hits;
    }
}

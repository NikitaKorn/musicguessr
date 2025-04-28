package com.petproject.musicguessr.client;

import com.petproject.musicguessr.model.outrequest.SearchGeniusResponse;
import com.petproject.musicguessr.model.outrequest.SongsGeniusResponse;

public interface GeniusClient {
    SearchGeniusResponse searchByText(String text);
    SongsGeniusResponse searchBySongId(String id);
}

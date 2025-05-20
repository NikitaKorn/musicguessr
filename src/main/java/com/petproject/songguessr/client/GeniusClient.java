package com.petproject.songguessr.client;

import com.petproject.songguessr.model.outrequest.SearchGeniusResponse;
import com.petproject.songguessr.model.outrequest.SongsGeniusResponse;

public interface GeniusClient {
    SearchGeniusResponse searchByText(String text);
    SongsGeniusResponse searchBySongId(String id);
}

package com.petproject.songguessr.client;

import com.petproject.outrequest.SearchGeniusResponse;
import com.petproject.outrequest.SongsGeniusResponse;

public interface GeniusClient {
    SearchGeniusResponse searchByText(String text);
    SongsGeniusResponse searchBySongId(String id);
}

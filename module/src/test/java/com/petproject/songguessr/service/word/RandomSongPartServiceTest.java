package com.petproject.songguessr.service.word;

import com.petproject.songguessr.AbstractTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

@Disabled
public class RandomSongPartServiceTest extends AbstractTest {
    @Autowired
    @Qualifier("randomSongPartService")
    private RandomSongPartService songPartService;

    @Test
    public void successInitRooms() {
        String word = songPartService.peekRandom();
        assertThat(
                word,
                anyOf(
                        equalTo("любовь"),
                        equalTo("сердце"),
                        equalTo("ночь")
                ));
    }
}

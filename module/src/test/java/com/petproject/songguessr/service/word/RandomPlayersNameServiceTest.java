package com.petproject.songguessr.service.word;

import com.petproject.songguessr.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

public class RandomPlayersNameServiceTest extends AbstractTest {
    @Autowired
    @Qualifier("randomPlayersNameService")
    private RandomPlayersNameService playersNameService;

    @Test
    public void successInitRooms() {
        String word = playersNameService.peekRandom();
        assertThat(
                word,
                anyOf(
                        equalTo("Танцующий Бах"),
                        equalTo("Шепчущий Шуберт")
                ));
    }
}

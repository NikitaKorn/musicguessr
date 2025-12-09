package com.petproject.songguessr.service.word;

import com.petproject.songguessr.utils.AppUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Random;

@Slf4j
public class RandomSongPartService implements RandomPeekService {
    private final Random random = new Random();
    private List<String> words;

    @Value("${words.file.path}")
    private String filePath;

    @PostConstruct
    private void init() {
        words = AppUtils.readLinesFromFile(filePath);
    }

    @Override
    public String peekRandom() {
        int number = random.nextInt(words.size());
        return words.get(number);
    }
}

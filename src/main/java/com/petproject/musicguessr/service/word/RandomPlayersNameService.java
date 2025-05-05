package com.petproject.musicguessr.service.word;

import com.petproject.musicguessr.utils.AppUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RandomPlayersNameService implements WordsService {
    private final Random random = new Random();
    private List<String> words;

    @Value("${player.file.path:src/main/resources/names.txt}")
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

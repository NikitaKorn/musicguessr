package com.petproject.musicguessr.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public final class AppUtils {
    private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();

    public static long getCurrentTimeInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public static Optional<?> getOptionalFrom(Object object) {
        return Optional.of(object);
    }

    public static List<String> readLinesFromFile(String filePath) {
        File file = new File(filePath);
        List<String> result = new ArrayList<>();

        try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            log.info("Loaded {} line from {}", result.size(), filePath);
            return result;
        } catch (IOException | NullPointerException e) {
            log.error("Failed to read file: {}", filePath, e);
            throw new IllegalStateException("Failed to load words from file", e);
        }
    }
}

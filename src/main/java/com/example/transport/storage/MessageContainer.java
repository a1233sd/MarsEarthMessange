package com.example.transport.storage;

import lombok.Data;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class MessageContainer {
    private final int totalSegments;
    private final String username;
    private final Map<Integer, String> segments = new ConcurrentHashMap<>();
    private final Instant lastUpdated = Instant.now();

    public void addSegment(int number, String payload) {
        segments.put(number, payload);
    }

    public boolean isComplete() {
        return segments.size() == totalSegments;
    }

    public String assembleMessage() {
        return IntStream.rangeClosed(1, totalSegments)
                .mapToObj(segments::get)
                .collect(Collectors.joining(""));
    }
}
package com.example.transport.kafka;

import com.example.transport.dto.SegmentDTO;
import com.example.transport.storage.SegmentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SegmentConsumer {

    private final SegmentStorage storage;

    @KafkaListener(topics = "segments", groupId = "transport-group")
    public void listen(SegmentDTO segment) {
        storage.addSegment(segment);
    }
}
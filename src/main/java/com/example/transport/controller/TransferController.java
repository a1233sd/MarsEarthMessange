package com.example.transport.controller;

import com.example.transport.dto.SegmentDTO;
import com.example.transport.storage.SegmentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final KafkaTemplate<String, SegmentDTO> kafkaTemplate;
    private final SegmentStorage segmentStorage; // Добавляем бин SegmentStorage

    @PostMapping
    public ResponseEntity<String> receiveSegment(@RequestBody SegmentDTO segment) {
        System.out.println(">>> Received segment in /transfer: " + segment);
        // Отправляем сегмент в Kafka. Обработка (segmentStorage.addSegment) происходит в KafkaConsumer.
        kafkaTemplate.send("segments", segment);
        return ResponseEntity.ok("Segment sent to Kafka");
    }
}

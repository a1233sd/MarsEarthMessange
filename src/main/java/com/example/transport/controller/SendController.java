package com.example.transport.controller;

import com.example.transport.dto.SendRequestDTO;
import com.example.transport.service.SegmentationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class SendController {

    private final SegmentationService segmentationService;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody SendRequestDTO request) {
        System.out.println(">>> Received /send request: " + request);
        segmentationService.segmentAndSend(request);
        return ResponseEntity.ok("Message segmented and sent");
    }
}
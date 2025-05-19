package com.example.transport.service;

import com.example.transport.dto.SegmentDTO;
import com.example.transport.dto.SendRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SegmentationService {

    private final RestTemplate restTemplate;
    private static final int SEGMENT_SIZE = 100; // Размер сегмента в байтах
    private final String CHANNEL_URL = "http://host.docker.internal:8000/code";

    public void segmentAndSend(SendRequestDTO request) {
        System.out.println(">>> SegmentationService.segmentAndSend called with: " + request);
        String data = request.getData();
        Charset charset = StandardCharsets.UTF_8; // Используем UTF-8 для кодирования строки в байты
        byte[] dataBytes = data.getBytes(charset); // Преобразуем строку в массив байтов
        int length = dataBytes.length;
        int totalSegments = (int) Math.ceil((double) length / SEGMENT_SIZE);
        System.out.println(">>> Total segments: " + totalSegments);

        for (int i = 0; i < totalSegments; i++) {
            int start = i * SEGMENT_SIZE;
            int end = Math.min(start + SEGMENT_SIZE, length);
            // Создаем строку из байтового сегмента
            String payload = new String(dataBytes, start, end - start, charset);

            SegmentDTO segment = new SegmentDTO(
                    i + 1, // Номер сегмента
                    totalSegments, // Общее количество сегментов
                    request.getUsername(),
                    payload, // Данные сегмента
                    request.getSendTime()
            );
            System.out.println(">>> Sending segment: " + segment);
            restTemplate.postForEntity(CHANNEL_URL, segment, Void.class);
        }
    }
}

package com.example.transport.storage;

import com.example.transport.dto.SegmentDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

@Component
public class SegmentStorage {

    private final Map<String, MessageContainer> storage = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate = new RestTemplate(); // Используем для отправки HTTP-запросов

    // Генерация составного ключа по sendTime и username
    private String generateKey(String sendTime, String username) {
        if (sendTime.startsWith(username + "_")) {
            return sendTime;
        }
        return username + "_" + sendTime;
    }

    public void addSegment(SegmentDTO segment) {
        String key = generateKey(segment.getSendTime(), segment.getUsername());
        MessageContainer container = storage.compute(key, (k, message) -> {
            if (message == null) {
                message = new MessageContainer(segment.getTotalSegments(), segment.getUsername());
            }
            message.addSegment(segment.getSegmentNumber(), segment.getPayload());
            return message;
        });

        // Если сообщение собрано полностью, отправляем его на сервис "Марс"
        if (container.isComplete()) {
            String assembledMessage = container.assembleMessage();
            System.out.println(">>> Assembled message: " + assembledMessage);

            // Формируем payload для отправки на endpoint /receive (сервис "Марс")
            Map<String, Object> payload = new HashMap<>();
            payload.put("username", container.getUsername());
            payload.put("data", assembledMessage);
            payload.put("sendTime", segment.getSendTime());

            try {
                String url = "http://host.docker.internal:8010/receive";
                restTemplate.postForEntity(url, payload, String.class);
                System.out.println(">>> Assembled message sent to Mars: " + payload);
            } catch (Exception e) {
                System.err.println("Проверка 8010!!! Error while sending assembled message: " + e.getMessage());
            }
            // Удаляем сообщение из хранилища после отправки
            removeMessage(segment.getSendTime(), container.getUsername());
        }
    }

    public Map<String, MessageContainer> getStorage() {
        return storage;
    }

    public void removeMessage(String sendTime, String username) {
        String key = generateKey(sendTime, username);
        System.out.println(">>> removeMessage called with key: " + key);
        int before = storage.size();
        storage.remove(key);
        int after = storage.size();
        System.out.println(">>> Storage size changed from " + before + " to " + after);
    }
}

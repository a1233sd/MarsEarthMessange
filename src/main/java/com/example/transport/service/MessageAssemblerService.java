//package com.example.transport.service;
//
//import com.example.transport.dto.ReceiveRequestDTO;
//import com.example.transport.storage.MessageContainer;
//import com.example.transport.storage.SegmentStorage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.Duration;
//import java.time.Instant;
//
//@Service
//@RequiredArgsConstructor
//public class MessageAssemblerService {
//
//    private final SegmentStorage storage;
//    private final RestTemplate restTemplate;
//    private final WebSocketSenderService webSocketSenderService;
//    private final String APPLICATION_URL = "http://localhost:3000/receive";
//    private static final Duration TIMEOUT = Duration.ofSeconds(5);
//
//    @Scheduled(fixedRate = 2000)
//    public void processMessages() {
////        System.out.println("🔄 processMessages() triggered");
//        storage.getStorage().forEach((sendTime, message) -> {
//            System.out.println("🔄 Checking message for sendTime: " + sendTime);
//            boolean timeout = Duration.between(message.getLastUpdated(), Instant.now()).compareTo(TIMEOUT) > 0;
//
//            if (message.isComplete()) {
//                ReceiveRequestDTO dto = new ReceiveRequestDTO(
//                        message.getUsername(),
//                        message.assembleMessage(),
//                        sendTime,
//                        ""
//                );
//                System.out.println("✅ Assembled complete message: " + dto);
//                System.out.println("📨 Sending WebSocket message: " + dto);
//                webSocketSenderService.sendMessageToFrontend(dto);
//
//                // Если нужно отправлять через REST:
//                //restTemplate.postForEntity(APPLICATION_URL, dto, Void.class);
//
//                // Лог перед удалением
//                int sizeBefore = storage.getStorage().size();
//                System.out.println(">>> Removing complete message with key: " + sendTime + ", user: " + message.getUsername());
//                System.out.println(">>> Storage size before remove: " + sizeBefore);
//
//                storage.removeMessage(sendTime, message.getUsername());
//
//                int sizeAfter = storage.getStorage().size();
//                System.out.println(">>> Storage size after remove: " + sizeAfter);
//
//            } else if (timeout) {
//                ReceiveRequestDTO dto = new ReceiveRequestDTO(
//                        message.getUsername(),
//                        "",
//                        sendTime,
//                        "lost"
//                );
//                System.out.println("❗ Message timeout or lost: " + dto);
//                System.out.println("📨 Sending WebSocket error message: " + dto);
//                webSocketSenderService.sendMessageToFrontend(dto);
//
//                restTemplate.postForEntity(APPLICATION_URL, dto, Void.class);
//
//                // Лог перед удалением
//                int sizeBefore = storage.getStorage().size();
//                System.out.println(">>> Removing lost message with key: " + sendTime + ", user: " + message.getUsername());
//                System.out.println(">>> Storage size before remove: " + sizeBefore);
//
//                storage.removeMessage(sendTime, message.getUsername());
//
//                int sizeAfter = storage.getStorage().size();
//                System.out.println(">>> Storage size after remove: " + sizeAfter);
//            }
//        });
//    }
//}

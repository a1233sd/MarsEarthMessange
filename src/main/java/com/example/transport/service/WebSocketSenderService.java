//package com.example.transport.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class WebSocketSenderService {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public void sendMessageToFrontend(Object message) {
//        System.out.println("📨 Sending message via WebSocket: " + message);
//        messagingTemplate.convertAndSend("/topic/messages", message);
//    }
//}

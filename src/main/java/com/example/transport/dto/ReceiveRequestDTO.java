package com.example.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveRequestDTO {
    private String username;
    private String text;
    private String sendTime;
    private String error;
}
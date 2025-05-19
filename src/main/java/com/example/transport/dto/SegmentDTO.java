package com.example.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentDTO {
    private int segmentNumber;
    private int totalSegments;
    private String username;
    private String payload;
    private String sendTime;
}
package com.evonX.demo.dto;

import com.evonX.demo.entity.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminRequestDTO {
    private Long id;
    private Long userId;
    private RequestStatus status;
    private LocalDateTime createdAt;
}

package com.evonX.demo.dto;

import com.evonX.demo.entity.EventType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class EventDTO {

    private Long id;
    private String title;
    private String image;
    private String description;
    private String category;
    private String university;
    private EventType eventType;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String meetingLink;
    private Long createdById;
    private LocalDateTime createdAt;

}

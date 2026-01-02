package com.evonX.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;  // URL or path to image

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String highlight;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String organizer;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false)
    private LocalTime eventTime;

    private String meetingLink;

    @Column(nullable = false)
    private String fee;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();
}

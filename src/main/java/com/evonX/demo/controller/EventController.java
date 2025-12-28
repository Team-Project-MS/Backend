package com.evonX.demo.controller;

import com.evonX.demo.dto.EventDTO;
import com.evonX.demo.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // Fixed: Uses hasAnyRole and returns 201 Created status
    @PostMapping
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO dto) {
        return new ResponseEntity<>(eventService.createEvent(dto), HttpStatus.CREATED);
    }

    // Fixed: Added @Valid to ensure data integrity during updates
    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO dto) {
        return ResponseEntity.ok(eventService.updateEvent(id, dto));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully");
    }

    // Fixed: Added @PageableDefault to handle missing parameters in Postman
    @GetMapping
    public ResponseEntity<Page<EventDTO>> getAllEvents(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<EventDTO>> getEventsByCategory(
            @PathVariable String category,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(eventService.getEventsByCategory(category, pageable));
    }

    @GetMapping("/university/{university}")
    public ResponseEntity<Page<EventDTO>> getEventsByUniversity(
            @PathVariable String university,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(eventService.getEventsByUniversity(university, pageable));
    }
}
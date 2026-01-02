package com.evonX.demo.service;

import com.evonX.demo.dto.EventDTO;
import com.evonX.demo.entity.Event;
import com.evonX.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    EventDTO createEvent(EventDTO dto);
    EventDTO updateEvent(Long id, EventDTO dto);
    void deleteEvent(Long id);
    EventDTO getEventById(Long id);
    Page<EventDTO> getAllEvents(Pageable pageable);
    Page<EventDTO> getEventsByCategory(String category, Pageable pageable);
    Page<EventDTO> getEventsByUniversity(String university, Pageable pageable);
    User getCurrentUser();
    Event findEventById(Long id);
    void ensureAdmin(User user);
    void ensureOwnerOrAdmin(User user, Event event);
    Event convertToEntity(EventDTO dto);
    void updateEntity(Event event, EventDTO dto);
    EventDTO convertToDTO(Event event);
}

package com.evonX.demo.service.Impl;

import com.evonX.demo.dto.EventDTO;
import com.evonX.demo.entity.Event;
import com.evonX.demo.entity.Role;
import com.evonX.demo.entity.User;
import com.evonX.demo.exception.EventNotFoundException;
import com.evonX.demo.exception.UnauthorizedAccessException;
import com.evonX.demo.repository.EventRepository;
import com.evonX.demo.repository.UserRepository;
import com.evonX.demo.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    // private final EmailService emailService;

    /* ================= CREATE ================= */



    public EventDTO createEvent(EventDTO dto) {
        User user = getCurrentUser();
        ensureAdmin(user);

        Event event = convertToEntity(dto);
        event.setCreatedBy(user);

        Event savedEvent = eventRepository.save(event);
        return convertToDTO(savedEvent);

        //emailService.sendNewEventNotification(event);


    }

    /* ================= UPDATE ================= */

    public EventDTO updateEvent(Long id, EventDTO dto) {
        User user = getCurrentUser();
        Event event = findEventById(id);

        ensureOwnerOrAdmin(user, event);

        updateEntity(event, dto);
        Event updatedEvent = eventRepository.save(event);

        return convertToDTO(updatedEvent);

        // emailService.sendUpdateEventNotification(event);

    }

    /* ================= DELETE ================= */

    public void deleteEvent(Long id) {
        User user = getCurrentUser();
        Event event = findEventById(id);

        ensureOwnerOrAdmin(user, event);
        eventRepository.delete(event);
    }

    /* ================= GET ================= */

    // ✅ PUBLIC – controller can call this
    public EventDTO getEventById(Long id) {
        return convertToDTO(findEventById(id));
    }

    public Page<EventDTO> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Page<EventDTO> getEventsByCategory(String category, Pageable pageable) {
        return eventRepository.findByCategory(category, pageable)
                .map(this::convertToDTO);
    }

    public Page<EventDTO> getEventsByUniversity(String university, Pageable pageable) {
        return eventRepository.findByUniversity(university, pageable)
                .map(this::convertToDTO);
    }

    /* ================= HELPERS ================= */

    public User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UnauthorizedAccessException("Authenticated user not found"));
    }

    public Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found with id: " + id));
    }

    public void ensureAdmin(User user) {
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.SUPER_ADMIN) {
            throw new UnauthorizedAccessException("Only admins can create events");
        }
    }

    public void ensureOwnerOrAdmin(User user, Event event) {
        boolean isOwner = event.getCreatedBy().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ADMIN || user.getRole() == Role.SUPER_ADMIN;

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedAccessException("Unauthorized to modify this event");
        }
    }

    /* ================= MAPPERS ================= */

    public Event convertToEntity(EventDTO dto) {
        return Event.builder()
                .title(dto.getTitle())
                .image(dto.getImage())
                .description(dto.getDescription())
                .highlight(dto.getHighlight())
                .university(dto.getUniversity())
                .organizer(dto.getOrganizer())
                .category(dto.getCategory())
                .eventType(dto.getEventType())
                .eventDate(dto.getEventDate())
                .eventTime(dto.getEventTime())
                .meetingLink(dto.getMeetingLink())
                .fee(dto.getFee())
                .build();
    }

    public void updateEntity(Event event, EventDTO dto) {
        event.setTitle(dto.getTitle());
        event.setImage(dto.getImage());
        event.setDescription(dto.getDescription());
        event.setHighlight(dto.getHighlight());
        event.setUniversity(dto.getUniversity());
        event.setOrganizer(dto.getOrganizer());
        event.setCategory(dto.getCategory());
        event.setEventType(dto.getEventType());
        event.setEventDate(dto.getEventDate());
        event.setEventTime(dto.getEventTime());
        event.setMeetingLink(dto.getMeetingLink());
        event.setFee(dto.getFee());
    }

    public EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setImage(event.getImage());
        dto.setDescription(event.getDescription());
        dto.setHighlight(event.getHighlight());
        dto.setUniversity(event.getUniversity());
        dto.setOrganizer(event.getOrganizer());
        dto.setCategory(event.getCategory());
        dto.setEventType(event.getEventType());
        dto.setEventDate(event.getEventDate());
        dto.setEventTime(event.getEventTime());
        dto.setMeetingLink(event.getMeetingLink());
        dto.setFee(event.getFee());
        dto.setCreatedById(event.getCreatedBy().getId());
        dto.setCreatedAt(event.getCreatedAt());
        return dto;
    }
}

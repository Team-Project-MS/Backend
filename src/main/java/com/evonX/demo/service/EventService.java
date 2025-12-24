package com.evonX.demo.service;

import com.evonX.demo.dto.EventDTO;
import com.evonX.demo.entity.Event;
import com.evonX.demo.entity.Role;
import com.evonX.demo.entity.User;
import com.evonX.demo.exception.EventNotFoundException;
import com.evonX.demo.exception.UnauthorizedAccessException;
import com.evonX.demo.repository.EventRepository;
import com.evonX.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    //private final EmailService emailService;

    public EventDTO createEvent(EventDTO dto) {
        User user = getCurrentUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.SUPER_ADMIN) {
            throw new UnauthorizedAccessException("Only admins can create events");
        }
        Event event = convertToEntity(dto);
        event.setCreatedBy(user);
        event = eventRepository.save(event);
        //emailService.sendNewEventNotification(event);
        return convertToDTO(event);
    }

    public EventDTO updateEvent(Long id, EventDTO dto) {
        User user = getCurrentUser();
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
        if (!event.getCreatedBy().getId().equals(user.getId()) && user.getRole() != Role.ADMIN && user.getRole() != Role.SUPER_ADMIN) {  //add ADMIN
            throw new UnauthorizedAccessException("Unauthorized to update this event");
        }
        updateEntity(event, dto);
        event = eventRepository.save(event);
       // emailService.sendUpdateEventNotification(event);
        return convertToDTO(event);
    }

    public void deleteEvent(Long id) {
        User user = getCurrentUser();
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
        if (!event.getCreatedBy().getId().equals(user.getId()) && user.getRole() != Role.ADMIN && user.getRole() != Role.SUPER_ADMIN) {  //add ADMIN
            throw new UnauthorizedAccessException("Unauthorized to delete this event");
        }
        eventRepository.delete(event);
    }

    public Page<EventDTO> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Page<EventDTO> getEventsByCategory(String category, Pageable pageable) {
        return eventRepository.findByCategory(category, pageable).map(this::convertToDTO);
    }

    public Page<EventDTO> getEventsByUniversity(String university, Pageable pageable) {
        return eventRepository.findByUniversity(university, pageable).map(this::convertToDTO);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    private Event convertToEntity(EventDTO dto) {
        return Event.builder()
                .title(dto.getTitle())
                .image(dto.getImage())
                .description(dto.getDescription())
                .university(dto.getUniversity())
                .category(dto.getCategory())
                .eventType(dto.getEventType())
                .eventDate(dto.getEventDate())
                .eventTime(dto.getEventTime())
                .meetingLink(dto.getMeetingLink())
                .build();
    }

    private void updateEntity(Event event, EventDTO dto) {
        event.setTitle(dto.getTitle());
        event.setImage(dto.getImage());
        event.setDescription(dto.getDescription());
        event.setUniversity(dto.getUniversity());
        event.setCategory(dto.getCategory());
        event.setEventType(dto.getEventType());
        event.setEventDate(dto.getEventDate());
        event.setEventTime(dto.getEventTime());
        event.setMeetingLink(dto.getMeetingLink());
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setImage(event.getImage());
        dto.setDescription(event.getDescription());
        dto.setUniversity(event.getUniversity());
        dto.setCategory(event.getCategory());
        dto.setEventType(event.getEventType());
        dto.setEventDate(event.getEventDate());
        dto.setEventTime(event.getEventTime());
        dto.setMeetingLink(event.getMeetingLink());
        dto.setCreatedById(event.getCreatedBy().getId());
        dto.setCreatedAt(event.getCreatedAt());
        return dto;
    }
}

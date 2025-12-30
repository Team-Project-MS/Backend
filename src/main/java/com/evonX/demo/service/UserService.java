package com.evonX.demo.service;

import com.evonX.demo.dto.EventDTO;
import com.evonX.demo.dto.UserDTO;
import com.evonX.demo.entity.*;
import com.evonX.demo.exception.EventNotFoundException;
import com.evonX.demo.exception.UnauthorizedAccessException;
import com.evonX.demo.exception.UserNotFoundException;
import com.evonX.demo.repository.AdminRequestRepository;
import com.evonX.demo.repository.EventRepository;
import com.evonX.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final AdminRequestRepository adminRequestRepository;

    public UserDTO getProfile() {
        User user = getCurrentUser();
        return convertToDTO(user);
    }

    public void subscribe() {
        User user = getCurrentUser();
        user.setSubscribed(true);
        userRepository.save(user);
    }

    public void unsubscribe() {
        User user = getCurrentUser();
        user.setSubscribed(false);
        userRepository.save(user);
    }

    public void requestAdmin() {
        User user = getCurrentUser();
        if (user.getRole() != Role.USER) {
            throw new UnauthorizedAccessException("Only users can request admin role");
        }
        if (adminRequestRepository.findByUserAndStatus(user, RequestStatus.PENDING).isPresent()) {
            throw new RuntimeException("Request already pending");
        }
        AdminRequest request = AdminRequest.builder().user(user).build();
        adminRequestRepository.save(request);
    }

    public List<EventDTO> getFavorites() {
        User user = getCurrentUser();
        return user.getFavorites().stream().map(this::convertToEventDTO).collect(Collectors.toList());
    }

    public void addToFavorites(Long eventId) {
        User user = getCurrentUser();
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found"));
        user.getFavorites().add(event);
        userRepository.save(user);
    }

    public void removeFromFavorites(Long eventId) {
        User user = getCurrentUser();
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found"));
        user.getFavorites().remove(event);
        userRepository.save(user);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirst_name(user.getFirst_name());
        dto.setLast_name(user.getLast_name());
        dto.setEmail(user.getEmail());
        dto.setUniversity(user.getUniversity());
        dto.setRole(user.getRole());

        // FIXED LINE:
        dto.setSubscribed(Boolean.TRUE.equals(user.getSubscribed()));

        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    private EventDTO convertToEventDTO(Event event) {
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

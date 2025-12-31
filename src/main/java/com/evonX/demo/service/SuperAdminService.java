package com.evonX.demo.service;

import com.evonX.demo.dto.AdminRequestDTO;
import com.evonX.demo.dto.UserDTO;
import com.evonX.demo.entity.AdminRequest;
import com.evonX.demo.entity.RequestStatus;
import com.evonX.demo.entity.Role;
import com.evonX.demo.entity.User;
import com.evonX.demo.exception.UnauthorizedAccessException;
import com.evonX.demo.repository.AdminRequestRepository;
import com.evonX.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuperAdminService {

    private final UserRepository userRepository;
    private final AdminRequestRepository adminRequestRepository;

    public List<UserDTO> getAllUsers() {
        checkSuperAdmin();
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }



    public List<UserDTO> getSubscribedUsers() {
        checkSuperAdmin();
        return userRepository.findBySubscribedTrue().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<AdminRequestDTO> getAdminRequests() {
        checkSuperAdmin();
        return adminRequestRepository.findByStatus(RequestStatus.PENDING).stream().map(this::convertToRequestDTO).collect(Collectors.toList());
    }

    public void approveAdminRequest(Long requestId) {
        checkSuperAdmin();
        AdminRequest request = adminRequestRepository.findById(requestId).orElseThrow();
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Request not pending");
        }
        request.setStatus(RequestStatus.APPROVED);
        request.getUser().setRole(Role.ADMIN);
        adminRequestRepository.save(request);
        userRepository.save(request.getUser());
    }

    public void rejectAdminRequest(Long requestId) {
        checkSuperAdmin();
        AdminRequest request = adminRequestRepository.findById(requestId).orElseThrow();
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Request not pending");
        }
        request.setStatus(RequestStatus.REJECTED);
        adminRequestRepository.save(request);
    }

    private void checkSuperAdmin() {
        User user = getCurrentUser();
        if (user.getRole() != Role.SUPER_ADMIN) {
            throw new UnauthorizedAccessException("Only super admins allowed");
        }
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
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
    private AdminRequestDTO convertToRequestDTO(AdminRequest request) {
        AdminRequestDTO dto = new AdminRequestDTO();
        dto.setId(request.getId());
        dto.setUserId(request.getUser().getId());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());
        return dto;
    }
}

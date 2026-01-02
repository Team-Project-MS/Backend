package com.evonX.demo.service.Impl;

import com.evonX.demo.entity.Role;
import com.evonX.demo.exception.UnauthorizedAccessException;
import com.evonX.demo.repository.UserRepository;
import com.evonX.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

// AdminService can use EventService for manage events, as admins can do the same as super admins for events.
// This service can be empty or extend if needed for admin-specific logic.

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    public void checkAdmin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmail(email).orElseThrow();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.SUPER_ADMIN) {
            throw new UnauthorizedAccessException("Only admins allowed");
        }
    }

    // Additional admin-specific methods if needed
}

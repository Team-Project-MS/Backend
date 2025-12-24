package com.evonX.demo.controller;

import com.evonX.demo.dto.AdminRequestDTO;
import com.evonX.demo.dto.UserDTO;
import com.evonX.demo.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_AMIN')")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(superAdminService.getAllUsers());
    }

    @GetMapping("/subscribed-users")
    public ResponseEntity<List<UserDTO>> getSubscribedUsers() {
        return ResponseEntity.ok(superAdminService.getSubscribedUsers());
    }

    @GetMapping("/admin-requests")
    public ResponseEntity<List<AdminRequestDTO>> getAdminRequests() {
        return ResponseEntity.ok(superAdminService.getAdminRequests());
    }

    @PostMapping("/admin-requests/{requestId}/approve")
    public ResponseEntity<String> approveAdminRequest(@PathVariable Long requestId) {
        superAdminService.approveAdminRequest(requestId);
        return ResponseEntity.ok("Approved");
    }

    @PostMapping("/admin-requests/{requestId}/reject")
    public ResponseEntity<String> rejectAdminRequest(@PathVariable Long requestId) {
        superAdminService.rejectAdminRequest(requestId);
        return ResponseEntity.ok("Rejected");
    }
}

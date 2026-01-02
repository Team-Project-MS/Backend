package com.evonX.demo.controller;

import com.evonX.demo.dto.AdminRequestDTO;
import com.evonX.demo.dto.UserDTO;
import com.evonX.demo.service.Impl.SuperAdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('SUPER_AMIN')")
public class SuperAdminController {

    private final SuperAdminServiceImpl superAdminServiceImpl;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(superAdminServiceImpl.getAllUsers());
    }

    @GetMapping("/subscribed-users")
    public ResponseEntity<List<UserDTO>> getSubscribedUsers() {
        return ResponseEntity.ok(superAdminServiceImpl.getSubscribedUsers());
    }

    @GetMapping("/admin-requests")
    public ResponseEntity<List<AdminRequestDTO>> getAdminRequests() {
        return ResponseEntity.ok(superAdminServiceImpl.getAdminRequests());
    }

    @PostMapping("/admin-requests/{requestId}/approve")
    public ResponseEntity<String> approveAdminRequest(@PathVariable Long requestId) {
        superAdminServiceImpl.approveAdminRequest(requestId);
        return ResponseEntity.ok("Approved");
    }

    @PostMapping("/admin-requests/{requestId}/reject")
    public ResponseEntity<String> rejectAdminRequest(@PathVariable Long requestId) {
        superAdminServiceImpl.rejectAdminRequest(requestId);
        return ResponseEntity.ok("Rejected");
    }
}

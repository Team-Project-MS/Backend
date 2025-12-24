package com.evonX.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription")
@PreAuthorize("isAuthenticated()")
public class SubscriptionController {

    // If needed, add more subscription logic here.
    @PostMapping("/toggle")
    public ResponseEntity<String> toggle() {
        // Implement if separate from user subscribe/unsubscribe
        return ResponseEntity.ok("Toggled");
    }
}

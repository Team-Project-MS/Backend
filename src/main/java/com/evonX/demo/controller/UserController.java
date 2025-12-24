package com.evonX.demo.controller;

import com.evonX.demo.dto.EventDTO;
import com.evonX.demo.dto.UserDTO;
import com.evonX.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe() {
        userService.subscribe();
        return ResponseEntity.ok("Subscribed");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe() {
        userService.unsubscribe();
        return ResponseEntity.ok("Unsubscribed");
    }

    @PostMapping("/request-admin")
    public ResponseEntity<String> requestAdmin() {
        userService.requestAdmin();
        return ResponseEntity.ok("Admin request submitted");
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<EventDTO>> getFavorites() {
        return ResponseEntity.ok(userService.getFavorites());
    }

    @PostMapping("/favorites/{eventId}")
    public ResponseEntity<String> addToFavorites(@PathVariable Long eventId) {
        userService.addToFavorites(eventId);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/favorites/{eventId}")
    public ResponseEntity<String> removeFromFavorites(@PathVariable Long eventId) {
        userService.removeFromFavorites(eventId);
        return ResponseEntity.ok("Removed from favorites");
    }
}

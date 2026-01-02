package com.evonX.demo.controller;

import com.evonX.demo.dto.EventDTO;
import com.evonX.demo.dto.UserDTO;
import com.evonX.demo.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() {
        return ResponseEntity.ok(userServiceImpl.getProfile());
    }



    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe() {
        userServiceImpl.subscribe();
        return ResponseEntity.ok("Subscribed");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe() {
        userServiceImpl.unsubscribe();
        return ResponseEntity.ok("Unsubscribed");
    }

    @PostMapping("/request-admin")
    public ResponseEntity<String> requestAdmin() {
        userServiceImpl.requestAdmin();
        return ResponseEntity.ok("Admin request submitted");
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<EventDTO>> getFavorites() {
        return ResponseEntity.ok(userServiceImpl.getFavorites());
    }

    @PostMapping("/favorites/{eventId}")
    public ResponseEntity<String> addToFavorites(@PathVariable Long eventId) {
        userServiceImpl.addToFavorites(eventId);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/favorites/{eventId}")
    public ResponseEntity<String> removeFromFavorites(@PathVariable Long eventId) {
        userServiceImpl.removeFromFavorites(eventId);
        return ResponseEntity.ok("Removed from favorites");
    }
}

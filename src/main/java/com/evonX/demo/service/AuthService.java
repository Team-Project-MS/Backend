/*package com.evonX.demo.service;

import com.evonX.demo.dto.AuthResponse;
import com.evonX.demo.dto.LoginDTO;
import com.evonX.demo.dto.RegisterDTO;
import com.evonX.demo.dto.UserDTO;
import com.evonX.demo.entity.Role;
import com.evonX.demo.entity.User;
import com.evonX.demo.repository.UserRepository;
import com.evonX.demo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .first_name(dto.getName())
                .last_name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .university(dto.getUniversity())
                .role(Role.USER)
                .subscribed(false)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUser(convertToDTO(user));

        return response;
    }

    public AuthResponse login(LoginDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUser(convertToDTO(user));

        return response;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirst_name(user.getFirst_name());
        dto.setLast_name(user.getLast_name());
        dto.setEmail(user.getEmail());
        dto.setUniversity(user.getUniversity());
        dto.setRole(user.getRole());
        dto.setSubscribed(user.isSubscribed());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
} */

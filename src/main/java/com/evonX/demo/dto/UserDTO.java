package com.evonX.demo.dto;

import com.evonX.demo.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private String university;
    private Role role;
    private Boolean subscribed;
    private LocalDateTime createdAt;
}

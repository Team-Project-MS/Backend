package com.evonX.demo.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
    UserDetails loadUserByUsername(String email);
}

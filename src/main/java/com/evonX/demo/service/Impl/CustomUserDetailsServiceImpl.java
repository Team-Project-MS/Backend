package com.evonX.demo.service.Impl;

import com.evonX.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // We find the user by email from MySQL
        // Because your com.evonX.demo.entity.User implements UserDetails,
        // we can return it directly.
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
    }
}
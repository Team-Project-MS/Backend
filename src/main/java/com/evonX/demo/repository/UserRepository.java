package com.evonX.demo.repository;

import com.evonX.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String mail);
    Boolean existsByEmail(String email);
    List<User> findBySubscribedTrue();
}

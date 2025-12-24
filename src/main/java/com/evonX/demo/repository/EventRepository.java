package com.evonX.demo.repository;

import com.evonX.demo.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAll(Pageable pageable);
    Page<Event> findByCategory (String category, Pageable pageable);
    Page<Event> findByUniversity(String university, Pageable pageable);
}

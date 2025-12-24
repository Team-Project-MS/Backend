package com.evonX.demo.repository;

import com.evonX.demo.entity.AdminRequestData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRequestDataRepository extends JpaRepository<AdminRequestData, Long> {
    Page<AdminRequestData> findAll (Pageable pageable);
}

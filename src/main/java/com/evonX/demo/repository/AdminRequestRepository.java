package com.evonX.demo.repository;

import com.evonX.demo.entity.AdminRequest;
import com.evonX.demo.entity.RequestStatus;
import com.evonX.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRequestRepository extends JpaRepository<AdminRequest, Long> {
    Optional<AdminRequest> findByUserAndStatus(User user, RequestStatus status);
    List<AdminRequest> findByStatus(RequestStatus status);
}

package com.evonX.demo.controller;

import com.evonX.demo.dto.AdminRequestDataDTO;
import com.evonX.demo.entity.AdminRequestData;
import com.evonX.demo.service.Impl.AdminRequestDataServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin-requests")
@RequiredArgsConstructor
public class AdminRequestDataController {

    private final AdminRequestDataServiceImpl service;

    @PostMapping
    public ResponseEntity<AdminRequestData> createAdminRequest(@RequestBody AdminRequestDataDTO dto) {
        AdminRequestData savedRequest = service.createAdminRequest(dto);
        return ResponseEntity.ok(savedRequest);
    }
}

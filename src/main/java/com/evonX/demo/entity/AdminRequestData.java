package com.evonX.demo.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AdminRequestData")
public class AdminRequestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String designation;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String nic;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private Integer phone_number;

    @Column(nullable = false)
    private String uniletterlink;

}

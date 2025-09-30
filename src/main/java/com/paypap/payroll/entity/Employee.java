package com.paypap.payroll.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "employee")
public class Employee {
    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "emp_number", nullable = false, unique = true)
    private String empNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String email;
    private String phone;
    private String bankName;
    private String bankAccount;
    private String taxId;

    private LocalDate dateHired;
    private LocalDate dateTerminated;

    private Instant createdAt;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> contracts;

    // Getters and setters...
}


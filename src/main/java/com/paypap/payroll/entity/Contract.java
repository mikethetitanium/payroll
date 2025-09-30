package com.paypap.payroll.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
@Table(name = "contract")
public class Contract {
    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "employee_id", columnDefinition = "CHAR(36)")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_type", nullable = false)
    private PayType payType;

    @Column(precision = 10, scale = 2) // Example: 99999999.99 max
    private BigDecimal baseSalary;

    @Column(columnDefinition = "CHAR(3)")
    private String currency;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    // Getters and setters...
}
